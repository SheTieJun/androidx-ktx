## DataStoreKit

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

#### 1. 只获取一次
```kotlin
launch {
    dataStoreKit.getFirst(key = "int", 1).toString()
}
```