import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.PersistentUserManagedCache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.spi.service.LocalPersistenceService;
import org.ehcache.impl.config.persistence.DefaultPersistenceConfiguration;
import org.ehcache.impl.config.persistence.UserManagedPersistenceContext;
import org.ehcache.impl.persistence.DefaultLocalPersistenceService;
import org.junit.Test;

import java.io.File;

/**
 * @author hy
 * @time 2018-05-10
 */
public class CacheTest {

    /**
     * 使用ehcache缓存
     */
    @Test
    public void testEhcache() {
        // 创建缓存管理器和一个叫“preConfigured”的缓存实例
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("cacheSing",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class,
                                String.class, ResourcePoolsBuilder.heap(10)))
                .build();
        // 初始化缓存管理器
        cacheManager.init();
        Cache cacheSing = cacheManager.getCache("cacheSing", Long.class, String.class);
        cacheSing.put(1L, "asd");
        // 覆盖上一个值
        cacheSing.put(1L, "qwe");
        System.out.println(cacheSing.get(1L));
        System.out.println(cacheSing.containsKey(1L));
        // 不存在的key-value返回null
        System.out.println(cacheSing.get(2L));
        // 不允许值为null，报空指针错误
        // preConfigured.put(3L, null);

        // 不在创建cacheManeger时创建缓存实例，单独创建
        Cache cache2 = cacheManager.createCache("cache2",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        Integer.class, String.class,
                        ResourcePoolsBuilder.heap(5)).build());

        cache2.put(100, "hello");
        System.out.println(cache2.get(100));
    }

    @Test
    public void testPersistenceCache() throws Exception {
        LocalPersistenceService persistenceService = new DefaultLocalPersistenceService(
                new DefaultPersistenceConfiguration(new File("G:\\workspace\\mybatisDemo\\ehcacheDemo\\ehcache")));

        PersistentUserManagedCache<Long, String> cache = UserManagedCacheBuilder.newUserManagedCacheBuilder(Long.class, String.class)
                .with(new UserManagedPersistenceContext<Long, String>("persistentCache", persistenceService))
                .withResourcePools(ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .disk(10L, MemoryUnit.MB, true))
                .build(true);
        // 把缓存只存进硬盘里，只要persistenceService相同，即使关闭，再次启动还是可以读取数据
        cache.put(2L, "asd");
        System.out.println(cache.get(2L));
        // cache.remove(44L);
        // 手动关闭和销毁
        cache.close();
        // 删除硬盘上的缓存
        // cache.destroy();

        // 停止服务
        persistenceService.stop();
    }

    public static void main(String[] args) {
//        CacheManager.
//                CacheManager cacheManager = CacheManager.create("./src/main/resources/ehcache.xml");
//
//        // 2. 获取缓存对象
//        Cache cache = cacheManager.getCache("HelloWorldCache");
//
//        // 3. 创建元素
//        Element element = new Element("key1", "value1");
//
//        // 4. 将元素添加到缓存
//        cache.put(element);
//
//        // 5. 获取缓存
//        Element value = cache.get("key1");
//        System.out.println(value);
//        System.out.println(value.getObjectValue());
//
//        // 6. 删除元素
//        cache.remove("key1");
//
//        Dog dog = new Dog(1L, "taidi", (short) 2);
//        Element element2 = new Element("taidi", dog);
//        cache.put(element2);
//        Element value2 = cache.get("taidi");
//        Dog dog2 = (Dog) value2.getObjectValue();
//        System.out.println(dog2);
//
//        System.out.println(cache.getSize());
//
//        // 7. 刷新缓存
//        cache.flush();
//
//        // 8. 关闭缓存管理器
//        cacheManager.shutdown();
    }
}
