 #!/usr/bin/env bash


 error_exit ()
 {
     echo "ERROR: $1 !!"
     exit 1
 }
 [[ ! -e "$JAVA_HOME/bin/java" ]] && JAVA_HOME=$HOME/jdk/java
 [[ ! -e "$JAVA_HOME/bin/java" ]] && JAVA_HOME=/usr/java
 [[ ! -e "$JAVA_HOME/bin/java" ]] && JAVA_HOME=/opt/taobao/java
 [[ ! -e "$JAVA_HOME/bin/java" ]] && unset JAVA_HOME

 if [[ -z "$JAVA_HOME" ]]; then
   if ${darwin}; then

     if [[ -x '/usr/libexec/java_home' ]] ; then
       export JAVA_HOME=`/usr/libexec/java_home`

     elif [[ -d "/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home" ]]; then
       export JAVA_HOME="/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home"
     fi
   else
     JAVA_PATH=`dirname $(readlink -f $(which javac))`
     if [[ "x$JAVA_PATH" != "x" ]]; then
       export JAVA_HOME=`dirname ${JAVA_PATH} 2>/dev/null`
     fi
   fi
   if [[ -z "$JAVA_HOME" ]]; then
         error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better!"
   fi
 fi

echo "JAVA_HOME: ${JAVA_HOME}"
export JAVA_HOME
CLASSPATH="${JAVA_HOME}/lib/tools.jar:${JAVA_HOME}/lib/dt.jar"
export CLASSPATH

echo "CLASSPATH: ${CLASSPATH}"