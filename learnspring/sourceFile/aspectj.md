> 记录一下aspectj遇到的顺序问题， 优先级问题


关于优先级的，下面有相应的描述；
```
Advice precedence
Multiple pieces of advice may apply to the same join point. In such cases, the resolution order of the advice is based on advice precedence.

Determining precedence
There are a number of rules that determine whether a particular piece of advice has precedence over another when they advise the same join point.

If the two pieces of advice are defined in different aspects, then there are three cases:

If aspect A is matched earlier than aspect B in some declare precedence form, then all advice in concrete aspect A has precedence over all advice in concrete aspect B when they are on the same join point.
Otherwise, if aspect A is a subaspect of aspect B, then all advice defined in A has precedence over all advice defined in B. So, unless otherwise specified with declare precedence, advice in a subaspect has precedence over advice in a superaspect.
Otherwise, if two pieces of advice are defined in two different aspects, it is undefined which one has precedence.
If the two pieces of advice are defined in the same aspect, then there are two cases:

If either are after advice, then the one that appears later in the aspect has precedence over the one that appears earlier.
Otherwise, then the one that appears earlier in the aspect has precedence over the one that appears later.
These rules can lead to circularity, such as

  aspect A {
      before(): execution(void main(String[] args)) {}
      after():  execution(void main(String[] args)) {}
      before(): execution(void main(String[] args)) {}
  }
such circularities will result in errors signalled by the compiler.

Effects of precedence
At a particular join point, advice is ordered by precedence.

A piece of around advice controls whether advice of lower precedence will run by calling proceed. The call to proceed will run the advice with next precedence, or the computation under the join point if there is no further advice.

A piece of before advice can prevent advice of lower precedence from running by throwing an exception. If it returns normally, however, then the advice of the next precedence, or the computation under the join pint if there is no further advice, will run.

Running after returning advice will run the advice of next precedence, or the computation under the join point if there is no further advice. Then, if that computation returned normally, the body of the advice will run.

Running after throwing advice will run the advice of next precedence, or the computation under the join point if there is no further advice. Then, if that computation threw an exception of an appropriate type, the body of the advice will run.

Running after advice will run the advice of next precedence, or the computation under the join point if there is no further advice. Then the body of the advice will run.

```