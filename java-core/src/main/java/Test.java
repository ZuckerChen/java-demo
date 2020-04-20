import java.util.PriorityQueue;

/**
 * @Author: Zucker
 * @Date: 2020/3/11 3:13 PM
 * @Description
 */
public class Test {

    public static void main(String[] args) {
        PriorityQueue<Person> queue = new PriorityQueue<Person>((c1, c2) -> c2.age - c1.age);
        queue.offer(new Person("chen", 13));
        queue.offer(new Person("zhang", 5));
        queue.offer(new Person("si", 8));

        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }

    static class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
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

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
