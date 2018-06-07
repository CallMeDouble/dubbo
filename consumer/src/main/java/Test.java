/**
 * Created by allan on 16-11-1.
 */
public class Test {

    private static boolean validateNullAndEmptyString(String string1, String string2) {
//        if(StringUtil.isNotNull(string1) || StringUtil.isNotNull(string2)){
//            return StringUtil.eq(string1,string2);
//        }else{
//            return StringUtil.isNull(string1) && StringUtil.isNull(string2);
//        }
        return true;
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
