package topaz.rendering;

import java.util.ArrayList;
import org.joml.Vector3f;
import topaz.physics.CollisionObject;
import topaz.physics.PhysicsManager;

public class GameObject {

    private Mesh mesh;
    private CollisionObject collisionObject;
    private ArrayList<GameObject> children = new ArrayList<>();

    //Currently shallow copies, maybe make deep copy later
    public GameObject(Mesh mesh, CollisionObject collisionObject) {
        this.mesh = mesh;
        this.collisionObject = collisionObject;
    }

    public void removeFromAllManagers(ObjectManager objectManager, RenderManager renderManager, PhysicsManager physicsManager) {
        objectManager.remove(this);
        renderManager.remove(mesh);
        physicsManager.remove(collisionObject);
    }

    public void attachChild(GameObject child) {
        children.add(child);
    }

    public void removeChild(GameObject child) {
        children.remove(child);
    }

    public void translate(Vector3f translation) {
        translate(translation.x, translation.y, translation.z);
    }

    public void translate(float dx, float dy, float dz) {
        mesh.translate(dx, dy, dz);
        collisionObject.translate(dx, dy, dz);

        for (int i = 0; i < children.size(); i++) {
            children.get(i).translate(dx, dy, dz);
        }
    }

    public void rotate(Vector3f rotation) {
        rotate(rotation.x, rotation.y, rotation.z);
    }

    //Bounding box doesn't work that well with rotations
    public void rotate(float dx, float dy, float dz) {
        mesh.rotate(dx, dy, dz);

        for (int i = 0; i < children.size(); i++) {
            children.get(i).rotate(dx, dy, dz);
        }
    }

    public void scale(Vector3f scale) {
        scale(scale.x, scale.y, scale.z);
    }

    public void scale(float dx, float dy, float dz) {
        mesh.scale(dx, dy, dz);
        collisionObject.scale(dx, dy, dz);

        for (int i = 0; i < children.size(); i++) {
            children.get(i).scale(dx, dy, dz);
        }
    }

    public void setLocation(Vector3f location) {
        setLocation(location.x, location.y, location.z);
    }

    public void setLocation(float x, float y, float z) {
        for (int i = 0; i < children.size(); i++) {
            Vector3f separationVector = new Vector3f(children.get(i).getLocation().sub(mesh.getLocation()));
            children.get(i).setLocation(x, y, z);
            children.get(i).translate(separationVector);
        }

        mesh.setLocation(x, y, z);
        collisionObject.setLocation(x, y, z);
    }

    public Vector3f getLocation() {
        return mesh.getLocation();
    }

    public void setRotation(Vector3f rotation) {
        setRotation(rotation.x, rotation.y, rotation.z);
    }

    //Bounding box doesn't work that well with rotations
    public void setRotation(float x, float y, float z) {
        for (int i = 0; i < children.size(); i++) {
            Vector3f separationVector = new Vector3f(children.get(i).getRotation().sub(mesh.getRotation()));
            children.get(i).setRotation(x, y, z);
            children.get(i).rotate(separationVector);
        }

        mesh.setRotation(x, y, z);
    }

    public Vector3f getRotation() {
        return mesh.getRotation();
    }

    public void setScale(Vector3f scale) {
        setScale(scale.x, scale.y, scale.z);
    }

    public void setScale(float x, float y, float z) {
        for (int i = 0; i < children.size(); i++) {
            Vector3f separationVector = new Vector3f(children.get(i).getScale().sub(mesh.getScale()));
            children.get(i).setScale(x, y, z);
            children.get(i).scale(separationVector);
        }

        mesh.setScale(x, y, z);
        collisionObject.setScale(x, y, z);
    }

    public Vector3f getScale() {
        return mesh.getScale();
    }

    public void enableCollisions(boolean collisions) {
        collisionObject.setActive(collisions);
    }

    public boolean isVisible() {
        return mesh.isVisible();
    }

    public void setVisible(boolean visible) {
        mesh.setVisible(visible);
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public CollisionObject getCollisionObject() {
        return collisionObject;
    }

    public void setCollisionObject(CollisionObject collisionObject) {
        this.collisionObject = collisionObject;
    }
}
