## DataStoreKit

> 注意：如果您需要支持大型或复杂数据集、部分更新或参照完整性，请考虑使用 Room，而不是 DataStore。DataStore 非常适合简单的小型数据集，但不支持部分更新或引用完整性。

> 重点：不支持部分更新或引用完整性,每次都是全部更新

基于：

```
'androidx.datastore:datastore-preferences:1.0.0'
```

### 创建

```kotlin
private val dataStoreKit by lazy { context.dataStoreKit() }
```

### 保存

```kotlin
launch {
    dataStoreKit.save(key = "int", 100)
}
```

### 获取

#### 持续获取值，默认null
```kotlin
launch {
    dataStoreKit.get<Int>(key = "int")
        .onEach {
            it.toString()
        }
        .collect()
}
```

#### 持续获取值，需要设置默认值,不可以null
```kotlin
launch {
    dataStoreKit.get(key = "int", 1)
        .onEach {
            it.toString() 
        }
        .collect()
}
```

#### 只获取一次
```kotlin
launch {
    dataStoreKit.getFirst(key = "int", 1).toString()
}
```

#### 移除单个key的值
```kotlin
launch {
    dataStoreKit.remove<Int>(key = "int") 
}
```

#### 清空
```kotlin
launch {
    dataStoreKit.clear()
}
```

#### 阻塞调用【非常规调用】
```kotlin
dataStoreKit.clearBlock()
dataStoreKit.getFirstBlock("int", -1)
dataStoreKit.saveBlock("int",i++)
```