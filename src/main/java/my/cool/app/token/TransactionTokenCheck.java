package my.cool.app.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CSRF対策、２重送信制御をメソッド単位で行うカスタムアノテーションです。
 * 
 * @author mitsui0273
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionTokenCheck {

    String value() default "";

    TransactionTokenType type() default TransactionTokenType.NONE;
}
