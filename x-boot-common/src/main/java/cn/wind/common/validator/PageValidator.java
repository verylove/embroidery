package cn.wind.common.validator;

import cn.wind.common.annotation.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author xukk
 * @date 2018/5/4
 */
public class PageValidator implements ConstraintValidator<Page, Pageable> {
    private int maxSize;

    @Override
    public void initialize(Page constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(Pageable value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        if (value.getPageSize() > this.maxSize) {
            context.disableDefaultConstraintViolation();//禁用默认的message的值
            //重新添加错误提示语句
            context
                    .buildConstraintViolationWithTemplate("超过每一页大小最大值(" + this.maxSize + ")").addConstraintViolation();
        } else return true;
        return false;
    }

}
