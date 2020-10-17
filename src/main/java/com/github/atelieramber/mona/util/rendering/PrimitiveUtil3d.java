package com.github.atelieramber.mona.util.rendering;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;

public class PrimitiveUtil3d {

    public static class Circle{
        
        public float radius, x, y, z;
        
        Vector3d facing = null;
        
        private Circle(float radius, float x, float y, float z, FacingDirection dir, FacingParity parity) {
            this.radius = radius;
            this.x = x;
            this.y = y;
            this.z = z;
            
            facing = new Vector3d(((dir != FacingDirection.X) ? parity.value : 0.0f), 
                                  ((dir != FacingDirection.Y) ? parity.value : 0.0f), 
                                  ((dir != FacingDirection.Z) ? parity.value : 0.0f));
        }
        private Circle(float radius, float x, float y, float z, Vector3d facing) {
            this.radius = radius;
            this.x = x;
            this.y = y;
            this.z = z;
            
            this.facing = facing;
        }
        
        public static Circle create(float radius, FacingDirection dir, FacingParity parity) {
            return create(radius, 0.0f, 0.0f, 0.0f, dir, parity);
        }
        
        public static Circle create(float radius, float x, float y, float z, FacingDirection dir, FacingParity parity) {
            return new Circle(radius, x, y, z, dir, parity);
        }
    }

    public static Vector3d v(float x, float y, float z) {
        return new Vector3d(x, y, z);
    }
    
    public static enum FacingParity{
        POSITIVE(1.0f),
        NEGATIVE(-1.0f);
        
        public float value;
        
        FacingParity(float value) {
            this.value = value;
        }
    }
    public static enum FacingDirection{
        X,
        Y,
        Z
    }
    
    public static class Rect{
        public float width, height;
        public Vector3d facing = null;
        
        Direction facingDir = null;
        
        public float x, y, z;
        
        public Vector3d v1; // Top left
        public Vector3d v2; // Top right
        public Vector3d v3; // Bottom left
        public Vector3d v4; // Bottom right
        
        public Vector3d[] verts;
        
        private Rect(float width, float height, float x, float y, float z, FacingDirection facing, FacingParity parity) {
            this(width, height, x, y, z, null, facing, parity);
        }
        private Rect(float width, float height, float x, float y, float z, Direction facingDir, FacingDirection facing, FacingParity parity) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
            this.z = z;
            
            verts = new Vector3d[4];
            
            float halfWidth = (width/2.0f) * ((facing != FacingDirection.X) ? 1.0f : 0.0f);
            float halfHeight = (height/2.0f) * ((facing != FacingDirection.Y) ? 1.0f : 0.0f);
            float halfDepth = (width/2.0f) * ((facing != FacingDirection.Z) ? 1.0f : 0.0f);
            v1 = v(-halfWidth + x,  halfHeight + y,  halfDepth + z); 
            v2 = v( halfWidth + x,  halfHeight + y,  halfDepth + z);
            v3 = v(-halfWidth + x, -halfHeight + y,  halfDepth + z);
            v4 = v( halfWidth + x, -halfHeight + y,  halfDepth + z);
            
            if(parity == FacingParity.NEGATIVE) {
                verts[0] = this.v4;
                verts[1] = this.v3;
                verts[3] = this.v2;
                verts[2] = this.v1;
            }else {
                verts[0] = this.v1;
                verts[1] = this.v2;
                verts[3] = this.v3;
                verts[2] = this.v4;
            }
            
            this.facingDir = facingDir;
            
            calculateNormal(parity);
        }
        
        private Rect(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, Vector3d facing) {
            this.width = (float)(v1.distanceTo(v2));
            this.height = (float)(v1.distanceTo(v3));
            
            Vector3d center = v1.add(v2).add(v3).add(v4).scale(.25f);
            this.x = (float) center.x;
            this.y = (float) center.y;
            this.z = (float) center.z;
            
            verts = new Vector3d[4];
            
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
            this.v4 = v4;
            
            this.facing = facing;
        }
        private Rect(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, FacingParity parity) {
            this(v1, v2, v3, v4, null, parity);
        }
        private Rect(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, Direction facing, FacingParity parity) {
            this.width = (float) (v1.distanceTo(v2));
            this.height = (float) (v1.distanceTo(v3));
            
            Vector3d center = v1.add(v2).add(v3).add(v4).scale(.25f);
            this.x = (float) center.x;
            this.y = (float) center.y;
            this.z = (float) center.z;
            
            verts = new Vector3d[4];
            
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
            this.v4 = v4;
            
            if(parity == FacingParity.NEGATIVE) {
                verts[1] = this.v4;
                verts[0] = this.v3;
                verts[2] = this.v2;
                verts[3] = this.v1;
            }else {
                verts[0] = this.v1;
                verts[1] = this.v2;
                verts[3] = this.v3;
                verts[2] = this.v4;
            }
            
            facingDir = facing;
            
            calculateNormal(parity);
        }
        
        public Vector3d calculateNormal(FacingParity parity) {
            if(facing != null) {
                return facing;
            }
            float pVal = parity.value;
            
            facing = v2.subtract(v1).crossProduct(v3.subtract(v1)).scale(pVal).normalize();
            
            return facing;
        }

        public static Rect create(float width, float height, FacingDirection dir, FacingParity parity) {
            return create(width, height, 0.0f, 0.0f, 0.0f, dir, parity);
        }
        
        /* Centered at x, y, z */
        public static Rect create(float width, float height, float x, float y, float z, FacingDirection facing, FacingParity parity) {
            return create(width, height, x, y, z, null, facing, parity);
        }
        /* Centered at x, y, z */
        public static Rect create(float width, float height, float x, float y, float z, Direction facingDir, FacingDirection facing, FacingParity parity) {
            return new Rect(width, height, x, y, z, facingDir, facing, parity);
        }
        
        public static Rect create(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, Vector3d facing) {
            return new Rect(v1, v2, v3, v4, facing);
        }
        public static Rect create(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, FacingParity parity) {
            return new Rect(v1, v2, v3, v4, parity);
        }
        public static Rect create(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4, Direction facing, FacingParity parity) {
            return new Rect(v1, v2, v3, v4, facing, parity);
        }
    }
    
    public static class Cube {
        public Rect south;
        public Rect north;
        
        public Rect east;
        public Rect west;
        
        public Rect up;
        public Rect down;
        
        public Rect[] rects;
        
        public float size;
        
        
        public float x, y, z;
        
        private Cube(float size) {
            this(size, 0.0f, 0.0f, 0.0f);
        }
        
        private Cube(float size, float x, float y, float z) {
            this.size = size;
            this.x = x;
            this.y = y;
            this.z = z;
            
            rects = new Rect[6];
            
            size /= 2.0f;
            // Z needs to be flipped due to OpenGL being -Z
            south = rects[0] = Rect.create(v(-size + x,  size + y,  size + z), v( size + x,  size + y,  size + z), v(-size + x, -size + y,  size + z), //+Z
                                           v( size + x, -size + y,  size + z), Direction.SOUTH, FacingParity.NEGATIVE); 
            north = rects[1] = Rect.create(v(-size + x,  size + y, -size + z), v( size + x,  size + y, -size + z), v(-size + x, -size + y, -size + z), //-Z
                                           v( size + x, -size + y, -size + z), Direction.NORTH, FacingParity.POSITIVE); 
            
            east  = rects[2] = Rect.create(v( size + x,  size + y, -size + z), v( size + x,  size + y,  size + z), v( size + x, -size + y, -size + z), //+X
                                           v( size + x, -size + y,  size + z), Direction.EAST, FacingParity.POSITIVE); 
            west  = rects[3] = Rect.create(v(-size + x,  size + y, -size + z), v(-size + x,  size + y,  size + z), v(-size + x, -size + y, -size + z), //-X
                                           v(-size + x, -size + y,  size + z), Direction.WEST, FacingParity.NEGATIVE); 
            
            up    = rects[4] = Rect.create(v(-size + x,  size + y,  size + z), v( size + x,  size + y,  size + z), v(-size + x,  size + y, -size + z), //+Y
                                           v( size + x,  size + y, -size + z), Direction.UP, FacingParity.POSITIVE); 
            down  = rects[5] = Rect.create(v(-size + x, -size + y,  size + z), v( size + x, -size + y,  size + z), v(-size + x, -size + y, -size + z), //-Y
                                           v( size + x, -size + y, -size + z), Direction.DOWN, FacingParity.NEGATIVE); 
            
        }
        
        public static Cube create(float size) {
            return create(size, 0.0f, 0.0f, 0.0f);
        }
        
        public static Cube create(float size, float x, float y, float z) {
            return new Cube(size, x, y, z);
        }
    }
}
