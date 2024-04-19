package mk.myLabs.No_7.ex1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;

class NoSuchRoomException extends Throwable {
    String message;

    public NoSuchRoomException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

class NoSuchUserException extends Throwable {
    String message;

    public NoSuchUserException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

class ChatRoom {
    public String chatRoomName;
    public Set<String> usernameSet;

    public ChatRoom(String chatRoomName) {
        this.chatRoomName = chatRoomName;
        this.usernameSet = new HashSet<>();
    }

    public void addUser(String username) {
        usernameSet.add(username);
    }

    public void removeUser(String username) {
        usernameSet.remove(username);
    }

    public boolean hasUser(String username) {
        return usernameSet.contains(username);
    }

    public int numUsers() {
        return usernameSet.size();
    }


    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append(chatRoomName).append("\n");
        if (numUsers() == 0)
            st.append("EMPTY").append("\n");
        else
            usernameSet.stream().sorted().forEach(x -> st.append(x).append("\n"));
        return st.toString();
    }
}

class ChatSystem {
    public Map<ChatRoom, TreeSet<String>> chatRooms;
    public Set<String> registeredUsers;

    public ChatSystem() {
        this.registeredUsers = new HashSet<>();
        this.chatRooms = new HashMap<>();
    }

    public void addRoom(String roomName) {
        chatRooms.put(new ChatRoom(roomName), new TreeSet<>());
    }

    public void removeRoom(String roomName) {
        chatRooms.remove(new ChatRoom(roomName));
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
        if (chatRooms.containsKey(new ChatRoom(roomName)))
            return new ChatRoom(roomName);
        else
            throw new NoSuchRoomException(roomName);
    }

    public void register(String userName) {
        chatRooms.entrySet().stream().
                min(Comparator.comparing(x -> x.getValue().size()))
                .ifPresent(x -> x.getValue().add(userName));
        registeredUsers.add(userName);
    }

    public void registerAndJoin(String userName, String roomName) {
        register(userName);
        chatRooms.get(new ChatRoom(roomName)).add(userName);
    }

    public void joinRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {
        if (chatRooms.containsKey(new ChatRoom(roomName))) {
            if (registeredUsers.contains(userName)) {
                chatRooms.get(new ChatRoom(roomName)).add(userName);
            } else {
                throw new NoSuchUserException(userName);
            }
        } else {
            throw new NoSuchRoomException(roomName);
        }
    }

    public void leaveRoom(String userName, String roomName) throws NoSuchUserException, NoSuchRoomException {
        if (chatRooms.containsKey(new ChatRoom(roomName))) {
            if (registeredUsers.contains(userName)) {
                chatRooms.get(new ChatRoom(roomName)).remove(userName);
            } else {
                throw new NoSuchUserException(userName);
            }
        } else {
            throw new NoSuchRoomException(roomName);
        }
    }

    public void followFriend(String username, String friendUsername) throws NoSuchUserException {
        if (registeredUsers.contains(username)) {
            chatRooms.forEach((key, value) -> {
                if (value.contains(friendUsername))
                    value.add(username);
            });
        } else {
            throw new NoSuchUserException(username);
        }
    }

}

public class ChatSystemTest {
    @SuppressWarnings("varargs")
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr.addUser(jin.next());
                if (k == 1) cr.removeUser(jin.next());
                if (k == 2) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if (n == 0) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr2.addUser(jin.next());
                if (k == 1) cr2.removeUser(jin.next());
                if (k == 2) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if (k == 1) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while (true) {
                String cmd = jin.next();
                if (cmd.equals("stop")) break;
                if (cmd.equals("print")) {
                    System.out.println(cs.getRoom(jin.next()) + "\n");
                    continue;
                }
                for (Method m : mts) {
                    if (m.getName().equals(cmd)) {
                        String params[] = new String[m.getParameterTypes().length];
                        for (int i = 0; i < params.length; ++i) params[i] = jin.next();
                        m.invoke(cs, (Object) params);
                    }
                }
            }
        }
    }

}

