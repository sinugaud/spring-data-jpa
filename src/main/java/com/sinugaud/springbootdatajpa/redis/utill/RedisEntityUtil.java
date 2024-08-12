package com.rutusoft.incidentmanagement.utill;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RedisEntityUtil<T> {

  private final RedisTemplate<String, Object> redisTemplate;

  public RedisEntityUtil(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  // Save a single entity and automatically map its relationships
  public void saveEntityWithMapping(String key, T entity, EntityIdExtractor<T> idExtractor,
                                    EntityParentIdExtractor<T> parentIdExtractor, String parentKey, String childKey) {
    String entityId = idExtractor.extractId(entity);
    redisTemplate.opsForHash().put(key, entityId, entity);

    // Automatically map entity to its parent
    if (parentIdExtractor != null) {
      String parentId = parentIdExtractor.extractParentId(entity);
      if (parentId != null) {
        redisTemplate.opsForList().rightPush(parentKey + ":" + parentId + ":" + childKey, entityId);
      }
    }
  }

  // Save a list of entities and map their relationships
  public void saveAllEntities(String key, List<T> entities, EntityIdExtractor<T> idExtractor,
                              EntityParentIdExtractor<T> parentIdExtractor, String parentKey, String childKey) {
    for (T entity : entities) {
      saveEntityWithMapping(key, entity, idExtractor, parentIdExtractor, parentKey, childKey);
    }
  }

  public T getEntity(String key, String id) {
    return (T) redisTemplate.opsForHash().get(key, id);
  }

  public void deleteEntity(String key, String id) {
    redisTemplate.opsForHash().delete(key, id);
  }

  public Map<Object, T> getAllEntities(String key) {
    return (Map<Object, T>) redisTemplate.opsForHash().entries(key);
  }

  public List<Object> getRelations(String parentKey, String parentId, String childKey) {
    return redisTemplate.opsForList().range(parentKey + ":" + parentId + ":" + childKey, 0, -1);
  }

  @FunctionalInterface
  public interface EntityIdExtractor<T> {
    String extractId(T entity);
  }

  @FunctionalInterface
  public interface EntityParentIdExtractor<T> {
    String extractParentId(T entity);
  }
}
