package com.alison.spring.util;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class JVMGCUtils {
    static private GarbageCollectorMXBean youngGC;
    static private GarbageCollectorMXBean fullGC;

    static {
        List<GarbageCollectorMXBean> gcMXBeanList = ManagementFactory.getGarbageCollectorMXBeans();
        for (final GarbageCollectorMXBean gcMXBean : gcMXBeanList) {
            String gcName = gcMXBean.getName();
            if (gcName == null) {
                continue;
            }
            //G1 Old Generation
            //Garbage collection optimized for short pausetimes Old Collector
            //Garbage collection optimized for throughput Old Collector
            //Garbage collection optimized for deterministic pausetimes Old Collector
            //G1 Young Generation
            //Garbage collection optimized for short pausetimes Young Collector
            //Garbage collection optimized for throughput Young Collector
            //Garbage collection optimized for deterministic pausetimes Young Collector
            if (fullGC == null &&
                    (gcName.endsWith("Old Collector")
                            || "ConcurrentMarkSweep".equals(gcName)
                            || "MarkSweepCompact".equals(gcName)
                            || "PS MarkSweep".equals(gcName))
            ) {
                fullGC = gcMXBean;
            } else if (youngGC == null &&
                    (gcName.endsWith("Young Generation")
                            || "ParNew".equals(gcName)
                            || "Copy".equals(gcName)
                            || "PS Scavenge".equals(gcName))
            ) {
                youngGC = gcMXBean;
            }
        }
    }//static

    //YGC名称
    static public String getYoungGCName() {
        return youngGC == null ? "" : youngGC.getName();
    }

    //YGC总次数
    static public long getYoungGCCollectionCount() {
        return youngGC == null ? 0 : youngGC.getCollectionCount();
    }

    //YGC总时间
    static public long getYoungGCCollectionTime() {
        return youngGC == null ? 0 : youngGC.getCollectionTime();
    }

    //FGC名称
    static public String getFullGCName() {
        return fullGC == null ? "" : fullGC.getName();
    }

    //FGC总次数
    static public long getFullGCCollectionCount() {
        return fullGC == null ? 0 : fullGC.getCollectionCount();
    }

    //FGC总次数
    static public long getFullGCCollectionTime() {
        return fullGC == null ? 0 : fullGC.getCollectionTime();
    }


}
