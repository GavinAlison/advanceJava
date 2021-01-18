# custom template 

https://zb.oschina.net/reward/detail.html?id=12556501

默认是生成如上,

因为我们使用Hibernate我想生成如下

@Column(name = "pm_Ht_Model")
public Long getPmHtModel() {
   return pmHtModel;
}

public void setPmHtModel(Long pmHtModel) {
   this.pmHtModel = pmHtModel;
}

现在卡在一个问题,pmHtModel转pm_Ht_Model下划线,不知道模板里怎么设计

失败：
无法获知idea的velocity语言如何加载java类



