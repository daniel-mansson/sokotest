package util;

public class Vec2 {
	public float x;
	public float y;
	
	public Vec2() {
		x = 0;
		y = 0;
	}
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2(Vec2 other) {
		this.x = other.x;
		this.y = other.y;
	}
	 
/*	public Vec2(org.lwjgl.util.vector.Vector2f other) {
		this.x = other.x;
		this.y = other.y;
	}*/
	
	public Vec2 clone() {
		return new Vec2(this);
	}
	

	public Vec2 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vec2 set(Vec2 other) {
		this.x = other.x;
		this.y = other.y;
		return this;
	}
	
	@Override
	public String toString() {
		return x + ", " + y;
	}

/*	public Vec2 set(org.lwjgl.util.vector.Vector2f other) {
		this.x = other.x;
		this.y = other.y;
		return this;
	}*/

	public Vec2 add(Vec2 other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vec2 sub(Vec2 other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}

	public Vec2 mul(Vec2 other) {
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}

	public Vec2 div(Vec2 other) {
		this.x /= other.x;
		this.y /= other.y;
		return this;
	}

	public Vec2 mul(float value) {
		this.x *= value;
		this.y *= value;
		return this;
	}
	
	public Vec2 div(float value) {
		value = 1.0f / value;
		this.x *= value;
		this.y *= value;
		return this;
	}
	
	public float normalize() {
		float length = length(this);
		div(length);
		return length;
	}
	
	public Vec2 rotate(float theta) {
		float cosT = (float)Math.cos(theta);
		float sinT = (float)Math.sin(theta);
		float tmpX = x;
		x = tmpX * cosT - y * sinT;
		y = tmpX * sinT + y * cosT;
		return this;
	}

	public static float length(Vec2 a) {
		return (float)Math.sqrt(a.x * a.x + a.y * a.y);
	}
	
	public static float lengthSq(Vec2 a) {
		return a.x * a.x + a.y * a.y;
	}

	public static float distance(Vec2 a, Vec2 b) {
		Vec2 temp = new Vec2(a);
		temp.sub(b);
		return length(temp);
	}

	public static float distanceSq(Vec2 a, Vec2 b) {
		Vec2 temp = new Vec2(a);
		temp.sub(b);
		return lengthSq(temp);
	}
	
	public static float dot(Vec2 a, Vec2 b) {
		return a.x * b.x + a.y * b.y;
	}

	public static boolean almostEqual(Vec2 a, Vec2 b, float epsilon) {
		return Vec2.lengthSq(Vec2.sub(a, b)) < epsilon;
	}

	/*
	 * Note: In a right hand coordinate system.
	 */
	public static Vec2 perpendicularCCW(Vec2 a) {
		return new Vec2(-a.y, a.x);
	}

	/*
	 * Note: In a right hand coordinate system.
	 */
	public static Vec2 perpendicularCW(Vec2 a) {
		return new Vec2(a.y, -a.x);
	}

	public static Vec2 add(Vec2 a, Vec2 b) {
		Vec2 result =  new Vec2(a);
		return result.add(b);
	}

	public static Vec2 sub(Vec2 a, Vec2 b) {
		Vec2 result =  new Vec2(a);
		return result.sub(b);
	}

	public static Vec2 mul(Vec2 a, Vec2 b) {
		Vec2 result =  new Vec2(a);
		return result.mul(b);
	}

	public static Vec2 div(Vec2 a, Vec2 b) {
		Vec2 result =  new Vec2(a);
		return result.div(b);
	}

	public static Vec2 mul(Vec2 a, float b) {
		Vec2 result =  new Vec2(a);
		return result.mul(b);
	}

	public static Vec2 div(Vec2 a, float b) {
		Vec2 result =  new Vec2(a);
		return result.div(b);
	}
}
