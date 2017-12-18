package com.flobberworm.sample;

import java.util.Arrays;
import java.util.List;

/**
 * Sample
 * Created by Kornan on 2017/12/18.
 */

public final class Sample {
    public static String string[] = {"Tiger 老虎", "Giraffe 长颈鹿 ", "Lion 狮子 "
            , "Deer 鹿 ", "Leopard 豹 ", "Monkey 猴子  ", "Elephant 大象  "
            , "Chimpanzees 黑猩猩  ", "Horse 马 ", "Bear 熊  ", "Donkey 驴  "
            , "Kangaroo 袋鼠 ", "Ox 牛   ", "Hedgehog 刺猬  ", "Sheep 绵羊  "
            , "Rhinoceros 犀牛  ", "Dog 狗 ", "Camel 骆驼 ", "Cat 猫  "
            , "Hippopotamus 河马 ", "Pig 猪   ", "Crocodile 鳄鱼 ", "Chicken 鸡肉 "
            , "Snake 蛇 ", "Rabbit 兔子 ", "Frog 青蛙  ", "Duck 鸭子 "
            , "Tortoise 乌龟  ", "Goose 鹅 ", "Fox 狐狸 ", "Panda 熊猫  "
            , "Squirrel 松鼠 ", "Zebra 斑马 ", "Mouse 老鼠  ", "Wolf 狼  "
            , "Peacock （雄）孔雀  ", "Owl 猫头鹰 ", "Sparrow 麻雀  "
            , "Toco toucan Miss ma 这个字典里没有 ", "shrimp 虾 "
            , "Dragofly 蜻蜓", "Fly 苍蝇；飞虫"};

    public static List<String> getSample() {
//        List<String> dataList = new ArrayList<>();
//        dataList.addAll(Arrays.asList(string).subList(0, 10));
        return Arrays.asList(string).subList(0, 20);
    }

    public static List<String> getSample(int num) {
//        List<String> dataList = new ArrayList<>();
//        dataList.addAll(Arrays.asList(string).subList(0, 10));
        return Arrays.asList(string).subList(0, num);
    }
}
