package CoreSource;/*
 * Copyright (c) 2020. Ivannikov Alexandr Romanovich.
 * The text of this program is protected by copyright. Using it for commercial or personal purposes is prohibited
 * All rights reserved.
 */

import Decoration.Colors;
import Exceptions.FieldException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Person implements Serializable, Comparable<Person> {
    public Person(Double height, Long weight, String passportID, Float x, Long y, long z) {
        this.height = height;
        this.weight = weight;
        this.passportID = passportID;
        this.location = new Location(x, y, z);
    }

    private Double height; //Поле может быть null, Значение поля должно быть больше 0
    private Long weight; //Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; //Значение этого поля должно быть уникальным, Строка не может быть пустой, Поле может быть null
    private Location location; //Поле не может быть null

    public long getPassportIDValue() {
        try {
            return passportID.length()*10;
        }catch (NullPointerException e){
            return 0;
        }
    }
    public long getHeightValue() {
        try {
            return Math.round(height);
        }catch (NullPointerException e){
            return 0;
        }
    }
    public long getWeightValue() {
            return weight;
    }
    public long getLocationValue() {
        return location.getValue();
    }

    public Answer printAscendingValues(){
        HashMap<Long,String> values = new HashMap<>();
        values.put(getHeightValue(),"Рост");
        values.put(getWeightValue(),"Вес");
        values.put(getPassportIDValue(),"Паспортные данныe");
        values.put(getLocationValue(),"Локация");
        AtomicReference<String> answer = new AtomicReference<>("");
        values.entrySet().stream().sorted(Map.Entry.<Long,String>comparingByKey()).forEach(longStringEntry -> answer.updateAndGet(v -> v + longStringEntry));
        return new Answer(answer.toString());
    }

    @Override
    public int compareTo(Person o) {

        if (o==null||o.height==null) return 1;
        if (this.height == null) return -1;
        return (int) (o.getHeight()*o.getWeight() - this.getHeight()*this.getWeight());
    }

    public class Location implements Serializable{

        private Float x; //Поле не может быть null
        private Long y; //Поле не может быть null
        private long z;

        @Override
        public String toString() {
            return "{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z;
        }

        public Location(Float x, Long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;

        }

        public Long getY() {
            if(y == null) throw new FieldException();
            else return y;
        }

        public Float getX() {
            if(x == null) throw new FieldException();
            else return x;
        }

        public long getZ() {
            return z;
        }

        public long getValue(){
            return  Math.round(x)+y+z;
        }
    }

    public Double getHeight() {
        if(height != null && height <= 0) throw new FieldException();
        else return height;
    }

    public Long getWeight() {
        if(weight == null || weight <= 0) throw new FieldException();
        else return weight;
    }

    public String getPassportID() {
        if(passportID != null && passportID.equals("")) throw new FieldException();
        else return passportID;
    }
    public Location getLocation() {
        if(location == null) throw new FieldException();
        else return location;
    }

    @Override
    public String toString() {
        return
                Colors.blue() + "\n  Рост: " + Colors.reset() + height +
                Colors.blue() + "\n  Вес: " + Colors.reset() + weight +
                Colors.blue() + "\n  Номер пасспорта: '" + Colors.reset() + passportID + '\'' +
                Colors.blue() + "\n  Локация: " + Colors.reset() + location.toString() +
                '}';
    }
}
