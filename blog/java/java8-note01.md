
Java8实战 自学笔记

由于新项目需要用到Java8，在此通过阅读Java8实战系统的学习java8的新特性，在此将学习资料整理便于以后复习。
声明：由于按照书中的章节来把每个章节的重点整理到笔记所以不像总结那样一目了然。
建议想系统的学习Java8又没有足够的时间看整本书的读者阅读。

通过Streams库避免synchronized编写代码，可以理解为特别的迭代器。
stream api 通过内部迭代避免不必要的循环，并且不受单核限制。
collection和stream的区别：collection主要是为了存储和访问数据，stream则主要用于描述对数据的计算。
stream提倡并行处理一个stream中元素。（顺序处理→Collection.stream()、多核并行处理→→Collection.parallelStream()）
java8优化并行难题有2点：
1.将大的流分成几个小的流，以便并行处理。
2.在没有可变共享状态时，函数或方法可以有效、安全地并行执行。

java8可以包含实现类没有提供实现的方法，接口有了默认实现。(接口声明声明中用心的default关键字来表示)
例如java8中可以直接调用List的sort方法，默认调用List接口中的default sort方法。

default void sort(Comparator<? super E> c) {
	Collections.sort(this, c);
}
* 一个类可以实现多个接口，当好几个接口中有多个默认实现，就意味着java中有了某种形式的多重基层。

行为参数化：就是一个方法接受多个不同的行为作为参数，并在内部使用它们，完成不同行为的能力。
可以把一个行为(一段代码，结合匿名类)通过接口封装起来后在调用时候通过Lambda方式实现，能够轻松的适应不断变化的需求。这种做法类似于策略设计模式。

Lambda表达式可以理解为匿名函数，它没有名称，但有参数列表、函数主体、返回类型，可能还有一个可以抛出的异常的列表。
函数式接口就是仅仅声明了一个抽象方法的接口。
只有在接受函数式接口的地方才可以使用Lambda表达式。
Lambda表达式可以作为参数传递给方法或存储在变量中。
Lambda表达式允许你直接内联，为函数式接口的抽象方法提供实现，并且将整个表达式作为函数式接口的一个实例。
Lambda表达式所需要代表的类型称为目标类型。
方法引用让你重复使用现有的方法实现并直接传递它们。



Lambda的基本语法：

(parameters) -> expression 例： () -> "Iron Man"
(parameters) -> { statements; } 例：() -> {return "Mario";}
参数列表——这里它采用了Comparator中compare方法的参数，两个Apple。
箭头——箭头->把参数列表与Lambda主体分隔开。
Lambda主体——比较两个Apple的重量。表达式就是Lambda的返回值了。
函数式接口就是只定义一个抽象方法的接口。

public interface Comparator<T> {
int compare(T o1, T o2);                   ← java.util.Comparator
}
public interface Runnable{
void run();                                ← java.lang.Runnable
}
函数式接口的抽象方法的签名基本上就是Lambda表达式的签名。函数式接口的抽象方法的签名称为函数描述符。(签名理解为返回类型)
Lambda表达式允许你直接以内联的形式为函数式接口的抽象方法提供实现，并把整个表达式作为函数式接口的实例（具体说来，是函数式接口一个具体实现的实例）

新的Java API @FunctionalInterface 这个标注用于表示该接口会设计成一个函数式接口。

Lambda环绕执行模式

记得行为参数化。(将不同的行为参数化)

使用函数式接口来传递行为。(定义函数式接口@FunctionalInterface)

执行一个行为。(声明复用方法参数接收函数式接口，实现中调用函数式接口的抽象方法)

传递Lambda。(调用复用方法参数传递函数式接口关联的Lambda)

Java 8的库设计师帮你在java.util.function包中引入了几个新的函数式接口。

Predicate<T>接口定义了一个名叫test的抽象方法，它接受泛型T对象，并返回一个boolean。
需要表示一个涉及类型T的布尔表达式时，就可以使用这个接口。
Consumer<T>定义了一个名叫accept的抽象方法，它接受泛型T的对象，没有返回（void）。
需要访问类型T的对象，并对其执行某些操作，就可以使用这个接口。
Function<T, R>接口定义了一个叫作apply的方法，它接受一个泛型T的对象，并返回一个泛型R的对象。
需要定义一个Lambda，将输入对象的信息映射到输出，就可以使用这个接口。
针对专门的输入参数类型的函数式接口的名称都要加上对应的原始类型前缀，比如DoublePredicate、IntConsumer、LongBinaryOperator、IntFunction等。Function接口还有针对输出参数类型的变种：ToIntFunction<T>、IntToDoubleFunction等。
注意，如果Lambda表达式抛出一个异常，那么抽象方法所声明的throws语句也必须与之匹配。

函数式接口	函数描述符(即Lambda表达式的签名)	原始类型特化
Predicate<T>	T->boolean	
IntPredicate,

LongPredicate,

DoublePredicate

Consumer<T>	T->void	
IntConsumer,

LongConsumer,

DoubleConsumer

Function<T,R>	T->R	IntFunction<R>,
IntToDoubleFunction,
IntToLongFunction,
LongFunction<R>,
LongToDoubleFunction,
LongToIntFunction,
DoubleFunction<R>,
ToIntFunction<T>,
ToDoubleFunction<T>,
ToLongFunction<T>
Supplier<T>	()->T	
BooleanSupplier,

IntSupplier,

LongSupplier,

DoubleSupplier

UnaryOperator<T>	T->T	
IntUnaryOperator,

LongUnaryOperator,

DoubleUnaryOperator

BinaryOperator<T>	(T,T)->T	
IntBinaryOperator,

LongBinaryOperator,

DoubleBinaryOperator

BiPredicate<L,R>	(L,R)->boolean	 
BiConsumer<T,U>	(T,U)->void	ObjIntConsumer<T>,
ObjLongConsumer<T>,
ObjDoubleConsumer<T>
BiFunction<T,U,R>	(T,U)->R	ToIntBiFunction<T,U>,
ToLongBiFunction<T,U>,
ToDoubleBiFunction<T,U>
 

同样的Lambda，不同的函数式接口：同一个Lambda表达式就可以与不同的函数式接口联系起来，只要它
们的抽象方法签名能够兼容。
特殊的void兼容规则：如果一个Lambda的主体是一个语句表达式， 它就和一个返回void的函数描述符兼容（当
然需要参数列表也兼容）
类型推断(可以省略参数类型)：Java编译器会从上下文（目标类型）推断出用什么函数式接口来配合Lambda表达式，这意味着它也可以推断出适合Lambda的签名，因为函数描述符可以通过目标类型来得到。这样做的好处在于，编译器可以了解Lambda表达式的参数类型，这样就可以在Lambda语法中省去标注参数类型。换句话说，Java编译器会像下面这样推断Lambda的参数类型。

Comparator<Apple> c =(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
Comparator<Apple> c =(a1, a2) -> a1.getWeight().compareTo(a2.getWeight());
Lambda使用局部变量的限制：。Lambda可以没有限制地捕获（也就是在其主体中引用）实例变量和静态变量。但局部变量必须显式声明为final，或事实上是final。换句话说，Lambda表达式只能捕获指派给它们的局部变量一次，否则无法编译。
原因：第一，实例变量都存储在堆中，而局部变量则保存在栈上。如果Lambda可以直接访问局部变量，而且Lambda是在一个线程中使用的，则使用Lambda的线程，可能会在分配该变量的线程将这个变量收回之后，去访问该变量。因此，Java在访问自由局部变量时，实际上是在访问它的副本，而不是访问原始变量。如果局部变量仅仅赋值一次那就没有什么区别了——因此就有了这个限制。
第二，这一限制不鼓励你使用改变外部变量的典型命令式编程模式（这种模式会阻碍很容易做到的并行处理）。

 

方法引用就是Lambda表达式的快捷写法。使用方法引用时，目标引用放在分隔符::前，方法的名称放在后面。

Lambda	等效的方法引用
(Apple a) -> a.getWeight()	Apple::getWeight
() -> Thread.currentThread().dumpStack()	Thread.currentThread()::dumpStack
(str, i) -> str.substring(i)	String::substring
(String s) -> System.out.println(s)	System.out::println
 

方法引用主要有三类：

指向静态方法的方法引用: (args) -> ClassName.staticMethod(args)    ClassName::staticMethod。
指向任意类型实例方法的方法引用: (arg0, rest) -> arg0.instanceMethod(rest)    ClassName::instanceMethod
指向现有对象的实例方法的方法引用: (args) -> expr.instanceMethod(args)    expr::instanceMethod。
构造函数引用：对于一个现有构造函数，你可以利用它的名称和关键字new来创建它的一个引用：ClassName::new。它的功能与指向静态方法的引用类似。例如，Lambda表达式() -> new Apple()构造函数引用为Apple::new

谓词复合：谓词接口包括三个方法：negate、and和or(从左向右确定优先级)，让你可以重用已有的Predicate来创建更复杂的谓词。
函数复合：把Function接口所代表的Lambda表达式复合起来。Function接口为此配了andThen和compose两个默认方法，它们都会返回Function的一个实例。
andThen：Function f = x -> x + 1; Function g = x -> x * 2; Function h = f.andThen(g); h.apply(1) = 4;
compose：Function f = x -> x + 1; Function g = x -> x * 2; Function h = f.compose(g); h.apply(1) = 3;

函数式数据处理
引入流：流是Java API的新成员，它允许你以声明性方式处理数据集合（通过查询语句来表达，而不是临时编写一个实现）。
元素序列——就像集合一样，流也提供了一个接口，可以访问特定元素类型的一组有序值。因为集合是数据结构，所以它的主要目的是以特定的时间/空间复杂度存储和访问元素（如ArrayList 与 LinkedList）。但流的目的在于表达计算，比如你前面见到的filter、sorted和map。集合讲的是数据，流讲的是计算。
源——流会使用一个提供数据的源，如集合、数组或输入/输出资源。 请注意，从有序集合生成流时会保留原有的顺序。由列表生成的流，其元素顺序与列表一致。
数据处理操作——流的数据处理功能支持类似于数据库的操作，以及函数式编程语言中的常用操作，如filter、map、reduce、find、match、sort等。流操作可以顺序执行，也可并行执行。
特点1：流水线——很多流操作本身会返回一个流，这样多个操作就可以链接起来，形成一个大的流水线。这让我们下一章中的一些优化成为可能，如延迟和短路。流水线的操作可以看作对数据源进行数据库式查询。
特点2：内部迭代——与使用迭代器显式迭代的集合不同，流的迭代操作是在背后进行的。
流与集合：集合与流之间的差异就在于什么时候进行计算。区别在于流只能遍历一次，并且它们遍历数据的方式不同。
集合是一个内存中的数据结构，它包含数据结构中目前所有的值——集合中的每个元素都得先算出来才能添加到集合中。
流则是在概念上固定的数据结构（你不能添加或删除元素），其元素则是按需计算的，流就像是一个延迟创建的集合：只有在消费者要求的时候才会计算值。|
外部迭代与内部迭代：Collection接口需用用户去迭代，这成为外部迭代。相反，Streams库使用内部迭代，不需要用户去迭代，只需要给出一个函数指定它干什么就可以了，并且内部迭代可以透明的并行处理。
流操作：称为中间操作，关闭流的操作称为终端操作。中间操作返回另一个流，只有触发一个终端操作才会从流的流水线生成结果。
循环合并：Streams尽管filter和map是两个独立的操作，但它们合并到同一次遍历中，把这种叫做循环合并。
使用流一般包括三件事：

一个数据源（如集合）来执行一个查询。
一个中间操作链，形成一条流的流水线；
一个终端操作，执行流水线，并能生成结果。
用谓词筛选-filter、筛选各异的元素-distinct、截短流-limit、跳过元素-skip

映射：

map 将一种类型的值转换为另外一种类型的值。
流的扁平化-flatMap 将多个Stream连接成一个Stream，这时候不是用新值取代Stream的值，与map有所区别，这是重新生成一个Stream对象取而代之。

查找和匹配：StreamAPI通过allMatch、anyMatch、noneMatch、findFirst和findAny方法提供了这样的工具。

Optional<T>类（java.util.Optional）是一个容器类，代表一个值存在或不存在。

isPresent()将在Optional包含值的时候返回true, 否则返回false。
ifPresent(Consumer<T> block)会在值存在的时候执行给定的代码块。我们在第3章介绍了Consumer函数式接口；它让你传递一个接收T类型参数，并返回void的Lambda表达式。
T get()会在值存在时返回值，否则抛出一个NoSuchElement异常。
T orElse(T other)会在值存在时返回值，否则返回一个默认值。
归约-reduce：把一个流中的元素组合起来，表达更复杂的查询。使用reduce的好处在于，这里的迭代被内部迭代抽象掉了，这让内部实现得以选择并行执行reduce操作。stream()换成了parallelStream()。
 

无线流必须结合limit限制流的大小：
Stream.iterate(0, n -> n + 2)按需依次对每个新生成的值应用函数的创建无限流。
Stream.generate(Math::random)接受一个Supplier<T>类型的Lambda提供新的值创建无限流。

自定义收集器：第一步：定义Collector类的签名，第二步：实现归约过程，第三步：让收集器并行工作（如果可能），第四步：finisher方法和收集器的characteristics方法

并行数据处理与性能：对顺序流调用parallel方法并不意味着流本身有任何实际的变化。它在内部实际上就是设了一个boolean标志，表示你想让调用parallel之后进行的所有操作都并行执行。类似地，你只需要对并行流调用sequential方法就可以把它变成顺序流。
并行流内部使用了默认的ForkJoinPool(线程数量等于处理器数量)，并行操作时应注意装箱拆箱的开销和拆分独立小块的开销。
正确使用并行流：错用并行流而产生错误的首要原因就是算法改变了某些共享状态。
有些操作本身在并行流上的性能就比顺序流差。特别是limit和findFirst等依赖于元素顺序的操作，它们在并行流上执行的代价非常大。(调用unordered方法来把有序流变成无序流)
对于较小的数据量，选择并行流几乎从来都不是一个好的决定。
要考虑流背后的数据结构是否易于分解。例如，ArrayList的拆分效率比LinkedList
高得多，因为前者用不着遍历就可以平均拆分，而后者则必须遍历。
流自身的特点，以及流水线中的中间操作修改流的方式，都可能会改变分解过程的性能。
还要考虑终端操作中合并步骤的代价是大是小
流的可分解性极佳的2个数据源是：ArrayList，IntStream.range。
分支/合并框架的目的是以递归方式将可以并行的任务拆分成更小的任务，然后将每个子任务的结果合并起来生成整体结果。

Spliterator是Java 8中加入的另一个新接口；这个名字代表“可分迭代器”（splitableiterator）。和Iterator一样，Spliterator也用于遍历数据源中的元素，但它是为了并行执行而设计的。

public interface Spliterator<T> {
    boolean tryAdvance(Consumer<? super T> action);
    Spliterator<T> trySplit();
    long estimateSize();
    int characteristics();
}
T是Spliterator遍历的元素的类型。
tryAdvance方法的行为类似于普通的Iterator。
trySplit是专为Spliterator接口设计的，因为它可以把一些元素划出去分给第二个Spliterator（由该方法返回），让它们两个并行处理。
Spliterator还可通过estimateSize方法估计还剩下多少元素要遍历，因为即使不那么确切，能快速算出来是一个值也有助于让拆分均匀一点。

接口静态方法：java8之后允许接口中定义静态方法，注意子类无需实现静态方法。
接口默认方法：是一种以源码兼容方式向接口内添加实现的方法，接口中用新default修饰符可以给接口提供默认的实现。
这使得子类不需要实现该方法。给旧的接口添加方法时候如果有很多子类，使用默认方法可以避免所有子类需要实现该接口。
java8中的接口和抽象类的区别：
接口中的默认方法，子类可以实现从而覆盖父类中的默认方法。
一个类只能继承一个抽象类，但是一个类可以实现多个接口。
一个抽象类可以通过实例变量（字段）保存一个通用状态，而接口是不能有实例变量的。
菱形继承相同函数签名的两个方法的三条规则：

类中的方法优先级最高。类或父类中声明的方法的优先级高于任何声明为默认方法的优先级。
如果无法依据第一条进行判断，那么子接口的优先级更高：函数签名相同时，优先选择拥有最具体实现的默认方法的接口，即如果B继承了A，那么B就比A更加具体。
最后，如果还是无法判断，继承了多个接口的类必须通过显式覆盖和调用期望的方法，显式地选择使用哪一个默认方法的实现。
Optional取代null
使用Optional而不是null的实际语义区别是：在声明变量时使用Optional<T>类型说明T类型是允许缺失的，而变量T是否为空只能依赖业务模型的理解，判断一个null是否属于该变量的有效范畴。
* 引入Optional类的意图并非要消除每一个null引用。与此相反，目标是帮助你更好地设计出普适的API，让程序员看到方法签名，就能了解它是否接受一个Optional的值。这种强制会让你更积极地将变量从Optional中解包出来，直面缺失的变量值。
创建 Optional 对象：

声明一个空的Optional：Optional.empty();
依据一个非空值创建Optional：Optional.of(T t);
可接受null的Optional：Optional.ofNullable(T t)；
Optional类支持map、flatMap、filter，它们在概念上与Stream类中对应的方法十分相似。

组合式异步编程：
java.util.concurrent.Future(since java5)接口表示一个异步计算（即调用线程可以继续运行，不会因为调用方法而阻塞）的结果。
java.util.concurrent.CompletableFuture(since java8)实现了Future接口。

thenApply：的功能相当于将CompletableFuture<T>转换成CompletableFuture<U>。
thenCompose：可以用于组合多个CompletableFuture，将前一个结果作为下一个计算的参数，它们之间存在着先后顺序。
thenCombine：整合两个计算结果。future1、future2之间是并行执行的，最后再将结果汇总。
thenAcceptBoth：跟thenCombine类似，但是返回CompletableFuture<Void>类型。
thenAccept：无返回结果，接收上一阶段的输出作为本阶段的输入。
thenRun：无返回结果，根本不关心前一阶段的输出，根本不不关心前一阶段的计算结果，因为它不需要输入参数。
allOf：工厂方法接收一个由CompletableFuture构成的数组，数组中的所有Completable-Future对象执行完成之后，它返回一个CompletableFuture<Void>对象。
anyOf：。该方法接收一个CompletableFuture对象构成的数组，返回由第一个执行完毕的CompletableFuture对象的返回值构成的Completable-Future<Object>。
调整线程池的大小：N-threads = N-CPU * U-CPU * (1 + W/C)
NCPU是处理器的核的数目，可以通过Runtime.getRuntime().availableProcessors()得到
UCPU是期望的CPU利用率（该值应该介于0和1之间）
W/C是等待时间与计算时间的比率。

新的日期和时间API：Java 8在java.time包中整合了很多Joda-Time的特性。
DateTimeFormatter：线程安全的格式化，能够以单例模式创建格式器实例。date.format(DateTimeFormatter)
LocalDate：日期
LocalTime：时间
LocalDateTime：合并日期和时间，便于人阅读使用。Instant，便于机器处理。
Duration - 处理有关基于时间的时间量。
Period - 处理有关基于时间的日期数量。
TemporalAdjuster：处理更复杂的操作，如 将日期调整到下个周日、下个工作日，或者是本月的最后一天等等。(import static java.time.temporal.TemporalAdjusters.*;)

dayOfWeekInMonth 创建一个新的日期，它的值为同一个月中每一周的第几天
firstDayOfMonth 创建一个新的日期，它的值为当月的第一天
firstDayOfNextMonth 创建一个新的日期，它的值为下月的第一天
firstDayOfNextYear 创建一个新的日期，它的值为明年的第一天
firstDayOfYear 创建一个新的日期，它的值为当年的第一天
firstInMonth 创建一个新的日期，它的值为同一个月中，第一个符合星期几要求的值
lastDayOfMonth 创建一个新的日期，它的值为当月的最后一天
lastDayOfNextMonth 创建一个新的日期，它的值为下月的最后一天
lastDayOfNextYear 创建一个新的日期，它的值为明年的最后一天
lastDayOfYear 创建一个新的日期，它的值为今年的最后一天
lastInMonth 创建一个新的日期，它的值为同一个月中，最后一个符合星期几要求的值
next/previous 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星期几要求的日期
nextOrSame/previousOrSame 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星期几要求的日期，如果该日期已经符合要求，直接返回该对象
TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(
temporal -> {
DayOfWeek dow =
DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
int dayToAdd = 1;
if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
return temporal.plus(dayToAdd, ChronoUnit.DAYS);
});
date = date.with(nextWorkingDay);
函数式编程是一种使用函数进行编程的方式。
具体实践了声明式编程（“你只需要使用不相互影响的表达式，描述想要做什么，由系统来选择如何实现”）和无副作用计算。
准则：被称为“函数式”的函数或方法都只能修改本地变量。除此之外，它引用的对象都应该是不可修改的对象(被称为函数式，函数或者方法不应该抛出任何异常)。
函数式的方法特征-引用透明性：如果一个函数只要传递同样的参数值，总是返回同样的结果，那这个函数就是引用透明的。
相对于Java语言中传统的递归，“尾—递”可能是一种更好的方式，它开启了一扇门，让我们有机会最终使用编译器进行优化。
————————————————
版权声明：本文为CSDN博主「有刁民想害朕」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/xiaoyao880609/java/article/details/82867852