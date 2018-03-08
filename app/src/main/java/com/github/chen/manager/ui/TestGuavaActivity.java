package com.github.chen.manager.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.chen.manager.R;

import java8.util.Optional;

/**
 * @author chen
 * @date 2018/3/5
 * @Description 测试Guava库
 * @version 1.0.0
 */
public class TestGuavaActivity extends AppCompatActivity {

    private static final String TAG = "TestGuavaActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, TestGuavaActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_guava);

        Person person = new Person();
        Optional<Person> personOptional = Optional.ofNullable(person);
        personOptional.ifPresent(System.out::println);
        Person object = new Person("raki",26,1);
        personOptional.orElse(object);
    }

    class Person {
        private String name;
        private int age;
        private int sex;

        public Person() {
        }

        public Person(String name, int age, int sex) {
            this.name = name;
            this.age = age;
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }
    }
}
