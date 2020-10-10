package util;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

/**
 * 
 * @author shadi
 *
 */
@Target(METHOD)
public @interface AvoidUsage {
	String reason() default "Avoid using this function!";
}
