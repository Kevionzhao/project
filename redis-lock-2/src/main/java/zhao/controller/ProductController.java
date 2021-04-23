package zhao.controller;

//import com.zhao.util.RedisService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhao.util.RedisService;

import java.util.UUID;

/**
 * @author Admin
 */
@RestController
public class ProductController {
    private  static final String REDIS_LOCK = "Share_lock";
    @Autowired
    RedisService redisService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Value("${server.port}")
    private String port;

    @Autowired
    RedissonClient redisson;

    @GetMapping("/deduct_stock")
    public String deductStock(){
        String currentMark = UUID.randomUUID().toString()+Thread.currentThread().getName();
        RLock redissonLock = redisson.getLock(REDIS_LOCK);
        redissonLock.lock();
        try {
//            Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK, currentMark, 10, TimeUnit.SECONDS);
//            String stock = stringRedisTemplate.opsForValue().get("stock");
//            int count = stock == null ? 0 : Integer.parseInt(stock);
//            if (!locked) {
//                return "抢购失败";
//            }
            int count = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (count > 0) {
                int realStock = count - 1;
                stringRedisTemplate.opsForValue().set("stock",String.valueOf(realStock));
                System.out.println("剩余库存"+ realStock + "from " + port);
                return "剩余库存"+ realStock +"======="+port;
            }else {
                System.out.println("库存不足"+ "from " + port);
                return  "库存不足";
            }
        }finally {
            //redis 事务模式来保证原子性
            //但是官方建议使用lua脚本来实现
//            while(true){
//                stringRedisTemplate.watch(REDIS_LOCK);
//                if (stringRedisTemplate.opsForValue().get(REDIS_LOCK).equals(currentMark)){
//                    stringRedisTemplate.setEnableTransactionSupport(true);
//                    stringRedisTemplate.multi();
//                    stringRedisTemplate.delete(REDIS_LOCK);
//                    List<Object> list = stringRedisTemplate.exec();
//                    if (list == null){
//                        continue;
//                    }
//                }
//                stringRedisTemplate.unwatch();
//                break;
//            }
            //避免所删除时异常
            if (redissonLock.isLocked()&& redissonLock.isHeldByCurrentThread()){
                    redissonLock.unlock();
            }
        }
    }
}
