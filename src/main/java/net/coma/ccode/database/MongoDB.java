package net.coma.ccode.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import net.coma.ccode.CCode;
import net.coma.ccode.managers.Code;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

@Getter
public class MongoDB extends AbstractDatabase {
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public MongoDB(@NotNull ConfigurationSection section) {
        String host = section.getString("host");
        int port = section.getInt("port");
        String databaseName = section.getString("database");

        String username = section.getString("username");
        String password = section.getString("password");
        String connectionString;

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty())
            connectionString = String.format("mongodb://%s:%s@%s:%d/%s", username, password, host, port, databaseName);
        else connectionString = String.format("mongodb://%s:%d/%s", host, port, databaseName);


        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase(Objects.requireNonNull(databaseName));
        collection = database.getCollection("code");
    }

    @Override
    public boolean isConnected() {
        return mongoClient != null;
    }

    @Override
    public void disconnect() {
        if (isConnected()) {
            mongoClient.close();
        }
    }

    public void createCollection() {
        database.createCollection("code");
    }

    @Override
    public void createCode(@NotNull String name, @NotNull String cmd, int uses) {
        if (!exists(name)) {
            Document doc = new Document("CODE", name)
                    .append("CMD", cmd)
                    .append("USES", uses)
                    .append("OWNERS", "");
            collection.insertOne(doc);
        }
    }

    @Override
    public boolean exists(@NotNull String name) {
        return collection.find(eq("CODE", name)).first() != null;
    }

    @Override
    public void redeemCode(@NotNull String name, @NotNull OfflinePlayer player) {
        Document doc = collection.find(eq("CODE", name)).first();
        if (doc != null) {
            int currentUses = doc.getInteger("USES");
            String command = doc.getString("CMD");

            if (currentUses <= 0) {
                collection.deleteOne(eq("CODE", name));
            } else {
                collection.updateOne(eq("CODE", name), new Document("$set", new Document("USES", currentUses - 1)));
                collection.updateOne(eq("CODE", name), new Document("$pull", new Document("OWNERS", player.getName())));

                if (!command.isEmpty()) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", Objects.requireNonNull(player.getName())));
            }
        }
    }

    @Override
    public void giveCode(@NotNull String code, @NotNull OfflinePlayer player) {
        collection.updateOne(eq("CODE", code), new Document("$addToSet", new Document("OWNERS", player.getName())));
    }

    @Override
    public boolean isOwned(@NotNull String code, @NotNull OfflinePlayer player) {
        Document doc = collection.find(eq("CODE", code)).first();
        if (doc != null) {
            List<String> owners = doc.getList("OWNERS", String.class);
            return owners != null && owners.contains(player.getName());
        }
        return false;
    }

    @Override
    public boolean isUsesZero(@NotNull String code) {
        Document doc = collection.find(eq("CODE", code)).first();
        if (doc != null) {
            int uses = doc.getInteger("USES");
            return uses == 0;
        }
        return false;
    }

    @Override
    public void takeCode(@NotNull String code, @NotNull String oldOwner, @NotNull String newOwner) {
        Document doc = collection.find(eq("CODE", code)).first();
        if (doc != null) {
            List<String> owners = doc.getList("OWNERS", String.class);

            if (owners != null) {
                int index = owners.indexOf(oldOwner);

                if (index != -1) {
                    owners.set(index, newOwner);
                    collection.updateOne(eq("CODE", code), new Document("$set", new Document("OWNERS", owners)));
                }
            }
        }
    }

    @Override
    public void deleteCode(@NotNull String code) {
        collection.deleteOne(eq("CODE", code));
    }

    @Override
    public void changeName(@NotNull String oldName, @NotNull String newName) {
        collection.updateOne(eq("CODE", oldName), new Document("$set", new Document("CODE", newName)));
    }

    @Override
    public void changeCommand(@NotNull String name, @NotNull String newCommand) {
        collection.updateOne(eq("CODE", name), new Document("$set", new Document("CMD", newCommand)));
    }

    @Override
    public void changeUses(@NotNull String name, int newUses) {
        collection.updateOne(eq("CODE", name), new Document("$set", new Document("USES", newUses)));
    }

    @Override
    public List<Code> getCodes(@NotNull OfflinePlayer player) {
        List<Code> codes = new ArrayList<>();
        collection.find(new Document("OWNERS", new Document("$regex", player.getName())))
                .filter(new Document("USES", new Document("$gte", 1)))
                .forEach((Document doc) -> {
                    String name = doc.getString("CODE");
                    String command = doc.getString("CMD");
                    int uses = doc.getInteger("USES");
                    codes.add(new Code(name, command, uses));
                });

        return codes;
    }

    @Override
    public void reconnect() {
        disconnect();
        new MongoDB(Objects.requireNonNull(CCode.getInstance().getConfiguration().getSection("database.mongodb")));
    }
}

