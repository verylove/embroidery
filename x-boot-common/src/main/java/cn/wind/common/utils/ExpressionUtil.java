package cn.wind.common.utils;

import com.google.common.collect.Maps;
import lombok.NonNull;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xukk
 * @date 2018/5/4
 */
public class ExpressionUtil {
    private static ExpressionParser parser = new SpelExpressionParser();

    public static String transfer(@NonNull String str, @NonNull List list) {
        Map<Integer, Object> map = Maps.newHashMap();
        for (int i = 0; i < list.size(); i++) {
            map.put(i, list.get(i));
        }
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("map", map);
        context.setRootObject(map);
        Expression randomPhrase = parser.parseExpression(
                str,
                new TemplateParserContext());
       return randomPhrase.getValue(context, String.class);
    }
}
