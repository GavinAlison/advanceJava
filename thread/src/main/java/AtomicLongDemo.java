import java.util.concurrent.atomic.AtomicLong;

/***
 * @Author Alison
 * @Date 2019/4/28
 **/

public class AtomicLongDemo {


    public static void main(String[] args) {
        AtomicLong a = new AtomicLong();
        a.set(0x0123456789ABCDEFL);
        System.out.printf("%20s : 0x%016X\n", "get()", a.get());
        System.out.printf("%20s : 0x%016X\n", "intValue()", a.intValue());
        System.out.printf("%20s : 0x%016x\n", "longValue()", a.longValue());
        System.out.printf("%20s : %s\n", "floatValue()", a.floatValue());
        System.out.printf("%20s : %s\n", "doubleValue()", a.doubleValue());

        System.out.printf("%20s : 0x%016X\n", "getAndDecrement()", a.getAndDecrement());
        System.out.printf("%20s : 0x%016X\n", "decrementAndGet()", a.decrementAndGet());
        System.out.printf("%20s : 0x%016X\n", "getAndIncrement()", a.getAndIncrement());
        System.out.printf("%20s : 0x%016X\n", "incrementAndGet()", a.incrementAndGet());

        System.out.printf("%20s : 0x%016X\n", "addAndGet(0x10)", a.addAndGet(0x10));
        System.out.printf("%20s : 0x%016x\n", "getAndAdd(0x10)", a.getAndAdd(0x10));

        System.out.printf("\n%20s : 0x%016X\n", "get()", a.get());

        System.out.printf("%20s : %s\n", "compareAndSet()", a.compareAndSet(0x12345679L, 0xFEDCBA9876543210L));
        System.out.printf("%20s : 0x%016X\n", "get()", a.get());
    }
}
