package de.suzufa.screwbox.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the Rotation of an Object.
 */
public final class Rotation implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final double MIN_VALUE = 0;
    private static final double MAX_VALUE = 360;
    private static final Rotation NONE = ofDegrees(MIN_VALUE);

    private final double degrees;

    private Rotation(final double degrees) {
        this.degrees = degrees % MAX_VALUE;
    }

    /**
     * Creates a new Rotation by the given {@link #degrees()}.
     */
    public static Rotation ofDegrees(final double degrees) {
        return new Rotation(degrees);
    }

    /**
     * Returns the {@link Rotation}-value of an objects momentum. This value equals
     * the angle between a vertical line and the {@link Vector} starting on the
     * button of this line.
     * 
     * @see #ofMomentum(Vector)
     */
    public static Rotation ofMomentum(final double x, final double y) {
        final double degrees = Math.toDegrees(Math.atan2(x, -1 * y));
        final double inRangeDegrees = degrees + Math.ceil(-degrees / 360) * 360;

        return Rotation.ofDegrees(inRangeDegrees);
    }

    /**
     * Returns the {@link Rotation}-value of an objects momentum. This value equals
     * the angle between a vertical line and the {@link Vector} starting on the
     * button of this line.
     * 
     * 
     * @see #ofMomentum(double, double)
     */
    public static Rotation ofMomentum(final Vector momentum) {
        return ofMomentum(momentum.x(), momentum.y());
    }

    /**
     * Creates a new Rotation of zero {@link #degrees()}.
     */
    public static Rotation none() {
        return NONE;
    }

    /**
     * Returns the radians value of this {@link Rotation}.
     */
    public double radians() {
        return Math.toRadians(degrees);
    }

    /**
     * Returns the degrees value of this {@link Rotation}.
     */
    public double degrees() {
        return degrees;
    }

    @Override
    public int hashCode() {
        return Objects.hash(degrees);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Rotation other = (Rotation) obj;
        return Double.doubleToLongBits(degrees) == Double.doubleToLongBits(other.degrees);
    }

    @Override
    public String toString() {
        return "Rotation [degrees=" + degrees + "]";
    }

    /**
     * Checks if there isn't any rotation.
     */
    public boolean isNone() {
        return degrees == MIN_VALUE;
    }

}
