/*package com.jpatest.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.StringOperation;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.querydsl.spatial.GeometryExpression;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class QueryDSLUtils {


    //region methods - select
    public static Expression[] asExpressionArray(Expression... items) {
        return items;
    }

    public static List<Expression> asExpressionList(Expression... items) {
        return Arrays.stream(items).collect(Collectors.toList());
    }

    public static Expression[] select(EntityPath target, Class template) {

        HashMap<String, Field> map = ArrayUtils.toHashMap(
                c -> c.getName(),
                c -> c,
                ClassUtils.getInstanceFields(template));

        ArrayList<Expression> list = new ArrayList<>();

        Field[] fields = FieldUtils.getAllFields(target.getClass());
        Arrays.stream(fields).forEach(c -> {

            int modifiers = c.getModifiers();
            if (!(Modifier.isPublic(modifiers) &&
                    Modifier.isFinal(modifiers)))
                return;

            Class<?> type = c.getType();

            if (map.containsKey(c.getName())) {
                try {
                    list.add((Expression) c.get(target));

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return list.toArray(new Expression[0]);
    }

    public static Expression[] selectAttributes(EntityPath target) {

        ArrayList<Expression> list = new ArrayList<>();

        Field[] fields = FieldUtils.getAllFields(target.getClass());
        Arrays.stream(fields).forEach(c -> {

            int modifiers = c.getModifiers();
            if (!(Modifier.isPublic(modifiers) &&
                    Modifier.isFinal(modifiers)))
                return;

            Class<?> type = c.getType();

            if (c.getName().toLowerCase().equals("shape") || GeometryExpression.class.isAssignableFrom(type))
                return;

            if (Expression.class.isAssignableFrom(type)) {

                try {
                    list.add((Expression) c.get(target));

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return list.toArray(new Expression[0]);
    }

    public static Expression[] selectExcept(EntityPath target, Expression... excludes) {

        ArrayList<Expression> list = new ArrayList<>();
        HashSet<Expression> expressions = ArrayUtils.toHashSet(excludes);

        Field[] fields = FieldUtils.getAllFields(target.getClass());
        Arrays.stream(fields).forEach(c -> {

            int modifiers = c.getModifiers();
            if (!(Modifier.isPublic(modifiers) &&
                    Modifier.isFinal(modifiers)))
                return;

            Class<?> type = c.getType();

            if (expressions.contains(ClassUtils.getValueFrom(target, c)))
                return;

            if (Expression.class.isAssignableFrom(type)) {

                try {
                    list.add((Expression) c.get(target));

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return list.toArray(new Expression[0]);
    }

    public static Expression getId(EntityPath target) {

        EntityContext context = EntityContext.from(target.getClass());
        return context.getIdExpression();
    }
    //endregion

    //region methods - fetch
    public static <T> T entityFrom(Tuple tuple, Expression[] expressions, Class<T> target) {

        if (tuple == null)
            return null;

        HashMap<String, Field> map = ArrayUtils.toHashMap(
                Field::getName,
                c -> c,
                Arrays.stream(FieldUtils.getAllFields(target)).filter(ClassUtils::isInstanceUsually).toArray(Field[]::new));

        return innerEntityFrom(tuple, expressions, map, target);
    }

    public static <T> List<T> entitiesFrom(List<Tuple> tuples, Expression[] expressions, Class<T> target) {

        if (tuples == null || tuples.size() == 0)
            return new ArrayList<>();

        HashMap<String, Field> map = ArrayUtils.toHashMap(
                c -> c.getName(),
                c -> c,
                Arrays.stream(FieldUtils.getAllFields(target)).filter(ClassUtils::isInstanceUsually).toArray(Field[]::new));

        ArrayList<T> list = new ArrayList<>();
        for (Tuple tuple : tuples) {
            list.add(innerEntityFrom(tuple, expressions, map, target));
        }

        return list;
    }

    private static <T> T innerEntityFrom(Tuple tuple, Expression[] expressions, HashMap<String, Field> map, Class<T> target) {

        T obj = ClassUtils.newInstance(target);

        Arrays.stream(expressions).forEach(c -> {

            Path path;

            if (c instanceof StringOperation) {
                StringOperation so = (StringOperation) c;
                String operator = so.getOperator().name();
                if (!"ALIAS".equals(operator))
                    throw new DataException(String.format("字段表达式 %s 未使用别名，请使用 as 方法为表达式创建别名", c));

                path = (Path) so.getArg(1);

            } else if (c instanceof Path) {
                path = (Path) c;

            } else {
                throw new DataException(String.format("字段表达式 %s 未使用别名，请使用 as 方法为表达式创建别名", c));
            }

            PathMetadata metadata = path.getMetadata();
            String name = metadata.getName();
            if (!map.containsKey(name))
                return;

            Object val = tuple.get(c);
            try {
                Field field = map.get(name);
                if (!field.isAccessible())
                    field.setAccessible(true);

                field.set(obj, val);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return obj;
    }
    //endregion

    //region methods - update

    public static JPAUpdateClause update(JPAUpdateClause update, EntityPath target, Object value) {

        AssertUtils.notNull(value, "value");

        EntityContext context = EntityContext.from(target.getClass());
        ClassUtils.scanFields(value, (field, obj) -> {

            Id annotation = field.getDeclaredAnnotation(Id.class);
            if (annotation != null)
                return true;

            Expression expression = context.getExpression(field.getName());
            if (expression == null)
                return true;

            update.set((Path) expression, obj);
            return true;
        });

        return update;
    }

    public static JPAUpdateClause update(JPAUpdateClause update, EntityPath target, Map<String, Object> values) {

        AssertUtils.notNull(values, "values");

        EntityContext context = EntityContext.from(target.getClass());
        values.keySet().forEach(c -> {

            Expression expression = context.getExpression(c);
            if (expression == null)
                return;

            update.set((Path) expression, values.get(c));
        });

        return update;
    }

    //endregion

}
*/