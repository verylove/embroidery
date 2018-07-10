package cn.wind.common.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/10 18:12
 * @Description:
 */
public class ObjectMapperUtils {
    private static ModelMapper modelMapper = new ModelMapper();

    /**
     * 模型映射器属性设置在下面的块中指定。
     * 默认属性匹配策略设置为严格参见 {@link MatchingStrategies}
     * 使用自定义映射添加 {@link ModelMapper#addMappings(PropertyMap)}
     */
    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private ObjectMapperUtils() {
    }

    /**
     * <p>注意:outClass对象必须具有没有参数的默认构造函数</p>
     *
     * @param <D>      类型的结果对象
     * @param <T>      要映射的源对象的类型
     * @param entity   需要映射的实体
     * @param outClass 类的结果对象
     * @return <code>outClass</code>类型的新对象.
     */
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    /**
     * <p>注意:outClass对象必须具有没有参数的默认构造函数</p>
     *
     * @param entityList 需要映射的实体列表 (param list of entity)
     * @param outCLass   结果列表元素的类 (result list element)
     * @param <D>        结果列表中的对象类型 (objects)
     * @param <T>        <code>entityList</code>中的实体类型
     * @return <code><D></code> type映射对象列表
     */
    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    /**
     * Maps {@code source} to {@code destination}.
     *
     * @param source      object to map from
     * @param destination object to map to
     */
    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }
}
