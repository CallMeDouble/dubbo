import org.apache.commons.lang.StringUtils;

/**
 * Created by allan on 16-11-1.
 */
public class Test {

    private static boolean validateNullAndEmptyString(String string1, String string2) {
        if(StringUtils.isNotBlank(string1) || StringUtils.isNotBlank((string2))){
            return StringUtils.equals(string1,string2);
        }else{
            return StringUtils.isBlank(string1) && StringUtils.isBlank(string2);
        }
    }

    public static void main(String...args) {
        System.out.println(validateNullAndEmptyString("",null));
        System.out.println(validateNullAndEmptyString("",""));
        System.out.println(validateNullAndEmptyString(null,null));
        System.out.println(validateNullAndEmptyString(null,""));
        System.out.println(validateNullAndEmptyString("1","1"));
        System.out.println(validateNullAndEmptyString("1",null));
        System.out.println(validateNullAndEmptyString("1",""));
    }
}
