## Entity Insert/Create Debugging

```java
public abstract class AbstractEntityInformation<T, ID> implements EntityInformation<T, ID> {
    ...
    public boolean isNew(T entity) {
      
        ID id = getId(entity);
        Class<ID> idType = getIdType();
        
        if (!idType.isPrimitive()) {
            return id == null;
        }
        
        if (id instanceof Number) {
            return ((Number) id).longValue() == 0L;
        }
        
        throw new IllegalArgumentException(String.format("Unsupported primitive id type %s", idType));
    }
}
```
