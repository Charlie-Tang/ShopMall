/**
 * 
 */
package com.shop.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


/**
 * @author tangqichang
 *
 * 2019年1月29日-下午5:58:15
 */
@Component
public class ValidatorImpl implements InitializingBean{
	
	private Validator validator;
	
	//实现校验方法并返回校验结果
	public ValidationResult validate(Object bean) {
		
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(bean);
		
		if (constraintViolations.size()>0) {
			
			result.setHasErrors(true);
			constraintViolations.forEach(ConstraintViolation->{
				String errMsg = ConstraintViolation.getMessage();
				String propertyName = ConstraintViolation.getPropertyPath().toString();
				result.getErrorMsgMap().put(propertyName, errMsg);
			});
		}
		
		return result;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//将hibernate validator 通过工厂的初始化方式使其实例化
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	
}
