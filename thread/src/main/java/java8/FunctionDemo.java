package java8;

/**
 * 那么什么是函数式接口（Functional Interface）呢？
 * 所谓函数式接口（Functional Interface）就是只包含一个抽象方法的声明。针对该接口类型的所有 Lambda 表达式都会与这个抽象方法匹配。
 */
public class FunctionDemo {
    /*
    注意：你可能会有疑问，Java 8 中不是允许通过 defualt 关键字来为接口添加默认方法吗？
    那它算不算抽象方法呢？答案是：不算。因此，你可以毫无顾忌的添加默认方法，
    它并不违反函数式接口（Functional Interface）的定义。
     */

//    @FunctionalInterface
//    为了保证一个接口明确的被定义为一个函数式接口（Functional Interface）

    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

    public static void main(String[] args) {
        FunctionDemo functionDemo = new FunctionDemo();
        functionDemo.function2();
    }

    public void function() {
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);
    }

    //Java 8 中允许你通过 :: 关键字来引用类的方法或构造器。上面的代码简单的示例了如何引用静态方法，
    // 当然，除了静态方法，我们还可以引用普通方法：
    public void function2() {
        Converter<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);
    }

    class Something {
        String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }

    public void bigCats() {
        Something something = new Something();
        Converter<String, String> converter = something::startsWith;
        String converted = converter.convert("java");
        System.out.println(converted);
    }


    class Person {
        String firstName;
        String lastName;

        Person() {
        }

        Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    interface PersonFactory<P extends Person> {
        P create(String firstname, String lastname);
    }

    //我们可以通过 :: 关键字来引用 Person 类的构造器，来代替手动去实现这个工厂接口：
//    Person::new 这段代码，能够直接引用 Person 类的构造器。然后 Java 编译器能够根据上下文选中正确的构造器去实现 PersonFactory.create 方法。
    public void proailurus() {
        PersonFactory<Person> personPersonFactory = Person::new;
        Person person = personPersonFactory.create("Peter", "Parker");
    }


    /**
     * 娱乐一下：
     * big cats 由BBC 推出，讲述了猫科类动物的种类与特色
     * 非常棒
     */
}
