package cn.wind.common.datetime;

/**
 * 金米袋
 * Created by xukk on 2018/3/14.
 */
public class ClassIdentical {
    /**
     * is compatible.
     *
     * @param c class.
     * @param o instance.
     * @return compatible or not.
     */
    public static boolean isCompatible(Class<?> c, Object o) {
        boolean pt = c.isPrimitive();
        if (o == null) {
            return !pt;
        }

        if (pt) {
            if (c == int.class) {
                c = Integer.class;
            } else if (c == boolean.class) {
                c = Boolean.class;
            } else if (c == long.class) {
                c = Long.class;
            } else if (c == float.class) {
                c = Float.class;
            } else if (c == double.class) {
                c = Double.class;
            } else if (c == char.class) {
                c = Character.class;
            } else if (c == byte.class) {
                c = Byte.class;
            } else if (c == short.class) {
                c = Short.class;
            }
        }
        if (c == o.getClass()) {
            return true;
        }
        return c.isInstance(o);
    }
}
