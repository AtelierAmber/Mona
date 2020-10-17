package com.github.atelieramber.mona.util.rendering;

import net.minecraft.util.math.MathHelper;

public class PrimitiveUtil2d {

    public static class Circle{
        
        public float radius, x, y;
        
        private Circle(float radius, float x, float y) {
            this.radius = radius;
            this.x = x;
            this.y = y;
        }
        private Circle(float radius, float x, float y, float z) {
            this.radius = radius;
            this.x = x;
            this.y = y;
        }
        
        public static Circle create(float radius) {
            return create(radius, 0.0f, 0.0f);
        }
        
        public static Circle create(float radius, float x, float y) {
            return new Circle(radius, x, y);
        }
    }
    
    public static class Vec2f{
        float x, y;
        
        public Vec2f(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float length() {
            return MathHelper.sqrt(this.x * this.x + this.y * this.y);
        }
        
        public void addImpl(Vec2f vec) {
            this.x += vec.x;
            this.y += vec.y;
        }
        
        public Vec2f add(Vec2f vec) {
            return new Vec2f(this.x + vec.x, this.y + vec.y);
        }
        
        public Vec2f scale(float val) {
            return new Vec2f(this.x * val, this.y * val);
        }
        
        public float distanceTo(Vec2f vec) {
            double d0 = vec.x - this.x;
            double d1 = vec.y - this.y;
            return MathHelper.sqrt(d0 * d0 + d1 * d1);
        }
        
        public static Vec2f v(float x, float y) {
            return new Vec2f(x, y);
        }
    }

    public static Vec2f v(float x, float y) {
        return new Vec2f(x, y);
    }
    
    public static class Rect{
        public float width, height;
        
        public float x, y;
        
        public Vec2f v1; // Top left
        public Vec2f v2; // Top right
        public Vec2f v3; // Bottom left
        public Vec2f v4; // Bottom right
        
        public Vec2f[] verts;
        
        private Rect(float width, float height, float x, float y) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
            
            float halfWidth = (width/2.0f);
            float halfHeight = (height/2.0f);
            v1 = v(-halfWidth + x,  halfHeight + y); 
            v2 = v( halfWidth + x,  halfHeight + y);
            v3 = v(-halfWidth + x, -halfHeight + y);
            v4 = v( halfWidth + x, -halfHeight + y);
            
            verts = new Vec2f[4];
            verts[0] = this.v1;
            verts[1] = this.v2;
            verts[3] = this.v3;
            verts[2] = this.v4;
        }
        
        private Rect(Vec2f v1, Vec2f v2, Vec2f v3, Vec2f v4) {
            this.width = (float)(v1.distanceTo(v2));
            this.height = (float)(v1.distanceTo(v3));
            
            Vec2f center = v1.add(v2).add(v3).add(v4).scale(.25f);
            this.x = (float) center.x;
            this.y = (float) center.y;
            
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
            this.v4 = v4;
            
            verts = new Vec2f[4];
            verts[0] = this.v1;
            verts[1] = this.v2;
            verts[3] = this.v3;
            verts[2] = this.v4;
        }

        public static Rect create(float width, float height) {
            return create(width, height, 0.0f, 0.0f);
        }
        
        /* Centered at x, y, z */
        public static Rect create(float width, float height, float x, float y) {
            return new Rect(width, height, x, y);
        }
        
        public static Rect create(Vec2f v1, Vec2f v2, Vec2f v3, Vec2f v4) {
            return new Rect(v1, v2, v3, v4);
        }
    }

    public static class Square extends Rect{
        
        public float size;
        
        private Square(float size, float x, float y) {
            super(size, size, x, y);
            this.size = size;
        }
        
        public Square create(float size) {
            return create(size);
        }
        public Square create(float size, float x, float y) {
            return new Square(size, x, y);
        }
    }
}
