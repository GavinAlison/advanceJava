package java8;

/**
 * 通过 default 关键字这个新特性，可以非常方便地对之前的接口做拓展，而此接口的实现类不必做任何改动。
 */
public class InterfaceDefault {
    interface Formula {
        double calculate(int a);

        default double sqrt(int a) {
            return Math.sqrt(a);
        }
    }

    public static void main(String[] args) {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };
        System.out.println(formula.calculate(100));
        System.out.println(formula.sqrt(16));

    }
}
