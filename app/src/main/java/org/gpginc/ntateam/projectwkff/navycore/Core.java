package org.gpginc.ntateam.projectwkff.navycore;

import com.google.android.gms.common.internal.HideFirstParty;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Core
{
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        String s = scanner.next();
        String s1 = NaVYCryptor.krypt(s);
        System.out.println(s1);
        System.out.println(NaVYCryptor.nkrypt(s1));

    }
    public static abstract class Storyboard<T extends StoryItem>
    {
        protected final LinkedHashMap<T, LinkedList<Integer>> mItems = new LinkedHashMap<>();

        protected String name;
        protected int maxTimeStep;

        public Storyboard(String name, int maxTimeStep) {
            this.name = name;
            this.maxTimeStep = maxTimeStep;
        }

        public Storyboard() {
        }

        public String getName() {
            return name;
        }

        public Storyboard setName(String name) {
            this.name = name;
            return this;
        }

        public int getMaxTimeStep() {
            return maxTimeStep;
        }

        public Storyboard setMaxTimeStep(int maxTimeStep) {
            this.maxTimeStep = maxTimeStep;
            return this;
        }

        public void add(T val, int when)
        {
            this.put(val, when);
        }

        void put(T val, int when)
        {
            if(mItems.containsKey(val))
            {
                mItems.get(val).add(when);
            }
            else
            {
                LinkedList<Integer> L = new LinkedList<>();
                L.add(when);
                mItems.put(val, L);
            }
        }

        public LinkedHashSet<T> storyFrom(int time)
        {
            LinkedList<T> l = new LinkedList<>();
            mItems.forEach((t, i) ->
                    i.forEach(in -> {
                        if(in == time) l.add(t);
                    }));
            return new LinkedHashSet<>(l);
        }

        public abstract void writeToJSON(File file) throws IOException;
    }

    public static class StoryItem<T>
    {
        /*private final LinkedHashMap<String, Map<String, Object>> mObjects = new LinkedHashMap<>(1000);

        public StoryItem (String[] keys, Object[] objects)
        {
            if(keys.length==objects.length)
            {
                for (int i = 0; i < keys.length; i++) {
                    mObjects.put(keys[i], parseMap(objects[i]));
                }
            } else throw new RuntimeException("Arrays must have same LENGTH");
        }

        public StoryItem put(String key, Object o)
        {
            mObjects.put(key, parseMap(o));
            return this;
        }

        Map<String, Object> parseMap(Object o)
        {
            LinkedMap<String, Object> out = new LinkedMap<>();
            out.put("CLASS", o.getClass());
            for (Field field : o.getClass().getFields()) {
                try {
                    out.put(field.getName(), field.get(o));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return out;
        }*/
        private final LinkedList<Action<T>> actions = new LinkedList<>();

        public StoryItem() {
        }

        public StoryItem addAction(T executor, String name, String act, int type)
        {
            Action<T> a = new Action<>(name, executor);
            a.addActionResult(act, type);
            actions.add(a);
            return this;
        }
        public void addResultToAction(String name, String act, int type)
        {
            actions.stream().filter(a -> a.name.equals(name)).findFirst().get().addActionResult(act, type);
        }

        public LinkedList<Action<T>> getActions() {
            return actions;
        }

        public boolean existsAction(String name)
        {
            return actions.stream().filter(a -> a.name.equals(name)).findAny().isPresent();
        }

        public LinkedHashSet<Action<T>> getActionsSet()
        {
            return new LinkedHashSet<>(this.actions);
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            actions.forEach(b::append);

            return "StoryItem{" +
                    "actions=" + b.toString() +
                    '}';
        }

        public final class Action<T>
        {

            public static final int INTERACTION = 0;
            public static final int INFO = 1;
            public static final int PROVIDER = 2;
            protected String name;
            protected final T executor;

            protected final LinkedHashMap<String, Integer> actionResults = new LinkedHashMap<>();

            public Action(String name, T executor) {
                this.name = name;
                this.executor = executor;
            }

            public void addActionResult(String name, int res)
            {
                this.actionResults.put(name,res);
            }

            public LinkedHashSet<String> getAllForResult(int result)
            {
                LinkedList<String> r = new LinkedList<>();
                actionResults.forEach((s,i) ->
                {
                    if(i==result) {
                        r.add(s);
                    }
                });
                return new LinkedHashSet<>(r);
            }

            public T getExecutor() {
                return executor;
            }

            public String getName() {
                return name;
            }

            @Override
            public String toString() {
                return "Action{" +
                        "name='" + name + '\'' +
                        ", executor=" + executor +
                        ", actionResults=" + actionResults +
                        '}';
            }
        }

    }

    /**
     * uSELESS NOW
     */
    public static class NaVYCryptor
    {

        private static final Map<String, String> hex = new LinkedHashMap<>();
        private static final Map<String, String> nhex = new LinkedHashMap<>();

        @HideFirstParty
        /** @hide **/
        static void genHex() {
            put("a", "¦▬Ï$yA7");
            put("b", "¦▬Ï$¶0");
            put("c", "¦▬Ï$È☺5");
            put("d", "¦▬Ï$y4");
            put("e", "¦▬Ï$646");
            put("f", "¦▬Ï$y═");
            put("g", "¦▬Ï$y╚");
            put("h", "¦▬Ï╩ô62☻");
            put("i", "¦▬Ï13#Ï");
            put("j", "¦▬Ï$y45");
            put("k", "¦▬87õªÛ");
            put("l", "¦▬Ï$y7");
            put("m", "¦4Ï╚-Ä-6~♂");
            put("n", "¦4ÖüÏ$y7");
            put("o", "¦ñRÏ$y7");
            put("p", "¦ÿöÏ$y7");
            put("q", "¦▀Ö╦ÓÏ$y7");
            put("r", "¦ýî$Bx!");
            put("s", "¦ÄøÆEYÈ");
            put("t", "¦¢¢ÏØµu(");
            put("u", "¦p»¢ÏÖ***");
            put("v", "¦3214586");
            put("w", "¦xÄ╚Ðñù");
            put("x", "¦▬6Ïxy35456");
            put("y", "¦▬5Ù5V");
            put("z", "¦▬i~C$y7");
            put("A", "¦§ÏÑ$y7");
            put("B", "¦§Ï$¶0");
            put("C", "¦§Ï$È☺5");
            put("D", "¦§Ï$y4");
            put("E", "¦§Ï$646");
            put("F", "¦§Ï$y═");
            put("G", "¦§Ï$y╚");
            put("H", "¦§Ï╩ô62☻");
            put("I", "¦§Ï13#Ï");
            put("J", "¦§Ï$y45");
            put("K", "¦§87↨õªÛ");
            put("L", "¦§Ï$y7");
            put("M", "¦§4Ï╚-Ä-6~♂");
            put("N", "[[¦4ÖüÏ$y7");
            put("O", "¦§ñRÏ$y7");
            put("P", "¦}}ÿöÏ$y7");
            put("Q", "¦▀♂Ö╦ÓÏ$y7");
            put("R", "¦´ýî$Bx!");
            put("S", "¦AÄøÆEYÈ");
            put("T", "¦P¢¢ÏØµu(");
            put("U", "¦p»¢ÏÖ*{}");
            put("V", "¦A3214586");
            put("W", "¦xÄ╚Ðdñù");
            put("X", "¦ß§6Ïxy35456");
            put("Y", "¦§5Ù5V");
            put("Z", "¦§i~C$y7");
            put("0", "¦◙");
            put("1", "¦☺");
            put("2", "¦☻");
            put("3", "¦♥");
            put("4", "¦♦");
            put("5", "¦♣");
            put("6", "¦♠");
            put("7", "¦•");
            put("8", "¦◘");
            put("9", "¦○");
            puts(":");
            puts(",");
            puts(".");
            puts(";");
            puts("/");
            puts("?");
            puts("-");
            puts("_");
            puts("+");
            puts("=");
            puts("|");
            puts("\\");
            puts("!");
            puts("'");
            puts("\"");
            puts("@");
        }


        public static String krypt(String toEncrypt)
        {
            genHex();
            StringBuilder B = new StringBuilder();
            String[] ss = toEncrypt.split("");
            for (String s : ss) {
                B.append(k(s));
                System.out.print(s + "::"+k(s) + " || ");
            }
            System.out.println(toEncrypt + " -> " + B.toString());
            return B.toString();
        }
        public static String nkrypt(String tonEncrypt)
        {
            genHex();

            StringBuilder B = new StringBuilder();
            String[] ss = tonEncrypt.split("¦");
            for (String s : ss) {
                B.append(nk(s));
                System.out.print(s + "::"+nk(s) + " || ");
            }
            System.out.println(tonEncrypt+ " -> " + B.toString());
            return B.toString();
        }

        static String k(String s)
        {
            return hex.containsKey(s) ? hex.get(s) : s;
        }

        static String nk(String s)
        {
            return nhex.containsKey("¦" + s) ? nhex.get("¦" +s) : s;
        }

        static void put(String s, String s2)
        {
            hex.put(s, s2);
            nhex.put(s2, s);
        }
        static void puts(String s)
        {
            hex.put(s, s);
            nhex.put(s, s);
        }
    }
}
