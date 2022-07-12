## LiveData 之操作符扩展


```kotlin
/*** @Author stj
 * * @Date 2021/10/8-18:38
 * * @Email 375105540@qq.com
 * * 间隔固定时间内，取最后一个
 */
fun <T> LiveData<T>.throttleLast(duration: Long = 1000L) 

/*** @Author stj
 * * @Date 2021/10/8-18:38
 * * @Email 375105540@qq.com
 * * 间隔固定时间内，取第一个的值
 */
fun <T> LiveData<T>.throttleFirst(duration: Long = 1000L)


/*** @Author stj
 * * @Date 2021/10/8-18:38
 * * @Email 375105540@qq.com
 * * 一定时间内，最新的值
 */
fun <T> LiveData<T>.throttleLatest(duration: Long = 1000L)  

/**  debounce
 * @Author stj
 * @Date 2021/10/8-18:38
 * @Email 375105540@qq.com
 * 2个值之间必须间隔，固定时间
 */
fun <T> LiveData<T>.debounce(duration: Long = 1000L) 


fun LiveData<Boolean>.isTrue() = value == true

```