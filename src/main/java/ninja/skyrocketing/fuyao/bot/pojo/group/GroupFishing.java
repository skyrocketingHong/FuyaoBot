package ninja.skyrocketing.fuyao.bot.pojo.group;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;

/**
 * @author skyrocketing Hong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupFishing {
    @TableField
    private Long groupId;

    @TableField
    private Long userId;

    private String fish1;

    private String fish2;

    private String fish3;

    private String fish4;

    private String fish5;

    //根据坑位ID插入鱼
    public GroupFishing(User user, String fishId, int slotId) {
        this.groupId = user.getGroupId();
        this.userId = user.getUserId();
        switch (slotId) {
            case 0, 1 -> this.fish1 = fishId;
            case 2 -> this.fish2 = fishId;
            case 3 -> this.fish3 = fishId;
            case 4 -> this.fish4 = fishId;
            case 5 -> this.fish5 = fishId;
        }
    }

    //获取空的鱼筐坑位序号，如果都不为空，则返回0
    public int getNullSlot() {
        if (fish1 == null) {
            return 1;
        } else if (fish2 == null) {
            return 2;
        } else if (fish3 == null) {
            return 3;
        } else if (fish4 == null) {
            return 4;
        } else if (fish5 == null) {
            return 5;
        } else {
            return 0;
        }
    }

    //根据鱼筐坑位插入
    public void setFishBySlotId(int slotId, String fishId) {
        switch (slotId) {
            case 0, 1 -> this.fish1 = fishId;
            case 2 -> this.fish2 = fishId;
            case 3 -> this.fish3 = fishId;
            case 4 -> this.fish4 = fishId;
            case 5 -> this.fish5 = fishId;
        }
    }

    //根据鱼筐坑位置空
    public void setNullBySlotId(int slotId) {
        switch (slotId) {
            case 1 -> this.fish1 = null;
            case 2 -> this.fish2 = null;
            case 3 -> this.fish3 = null;
            case 4 -> this.fish4 = null;
            case 5 -> this.fish5 = null;
        }
    }

    //获取鱼筐占用的坑位数
    public int getSlotCount() {
        int count = 0;
        if (fish1 != null) {
            ++count;
        } if (fish2 != null) {
            ++count;
        } if (fish3 != null) {
            ++count;
        } if (fish4 != null) {
            ++count;
        } if (fish5 != null) {
            ++count;
        }
        return count;
    }

    //根据坑位获取对应坑位的鱼
    public String getFishBySlot(int slotId) {
        switch (slotId) {
            case 0, 1 -> {
                return fish1;
            }
            case 2 ->  {
                return fish2;
            }
            case 3 ->  {
                return fish3;
            }
            case 4 ->  {
                return fish4;
            }
            case 5 ->  {
                return fish5;
            }
        }
        return null;
    }
}