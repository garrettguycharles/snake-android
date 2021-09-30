package com.garrettcharles.gamelibrary;

public class Rect {

    private final android.graphics.RectF rect;

    float x;
    float y;
    float w;
    float h;

    float left;
    float top;
    float right;
    float bottom;

    Point topLeft;
    Point midTop;
    Point topRight;

    Point midLeft;
    Point center;
    Point midRight;

    Point bottomLeft;
    Point midBottom;
    Point bottomRight;

    public Rect(float left, float top, float width, float height) {
        this.w = width;
        this.h = height;
        this.x = left;
        this.y = top;
        this.left = left;
        this.top = top;
        this.right = left + width;
        this.bottom = top + height;

        this.rect = new android.graphics.RectF(left, top, right, bottom);

        this.topLeft = new Point(0, 0);
        this.midTop = new Point(0, 0);
        this.topRight = new Point(0, 0);

        this.midLeft = new Point(0, 0);
        this.center = new Point(0, 0);
        this.midRight = new Point(0, 0);

        this.bottomLeft = new Point(0, 0);
        this.midBottom = new Point(0, 0);
        this.bottomRight = new Point(0, 0);

        refresh();
    }

    private void refresh() {
        refreshPoints();
        refreshRect();
    }

    private void refreshPoints() {
        topLeft.x = left;
        topLeft.y = top;

        midTop.x = centerX();
        midTop.y = top;

        topRight.x = right;
        topRight.y = top;

        midLeft.x = left;
        midLeft.y = centerY();

        center.x = centerX();
        center.y = centerY();

        midRight.x = right;
        midRight.y = centerY();

        bottomLeft.x = left;
        bottomLeft.y = bottom;

        midBottom.x = centerX();
        midBottom.y = bottom;

        bottomRight.x = right;
        bottomRight.y = bottom;
    }

    private void refreshRect() {
        rect.left = this.left;
        rect.top = this.top;
        rect.right = this.right;
        rect.bottom = this.bottom;
    }

    public android.graphics.RectF getRect() {
        return rect;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public float centerX() {
        return (left + right) / 2;
    }

    public float centerY() {
        return (top + bottom) / 2;
    }

    public void setX(float x) {
        this.x = x;

        this.left = this.x;
        this.right = this.left + this.w;
        refresh();
    }

    public void setY(float y) {
        this.y = y;

        this.top = this.y;
        this.bottom = this.top + this.h;
        refresh();
    }

    public void setW(float w) {
        this.w = w;

        this.right = this.left + this.w;
        refresh();
    }

    public void setH(float h) {
        this.h = h;

        this.bottom = this.top + this.h;
        refresh();
    }

    public void setLeft(float x) {
        this.setX(x);
    }

    public void setTop(float y) {
        this.setY(y);
    }

    public void setRight(float x) {
        this.setX(x - this.w);
    }

    public void setBottom(float y) {
        this.setTop(y - this.h);
    }

    public void yPlusEquals(float i) {
        this.setY(this.y + i);
    }

    public void xPlusEquals(float i) {
        this.setX(this.x + i);
    }

    public void leftPlusEquals(float i) {
        this.setLeft(this.left + i);
    }

    public void topPlusEquals(float i) {
        this.setTop(this.top + i);
    }

    public void rightPlusEquals(float i) {
        this.setRight(this.right + i);
    }

    public void bottomPlusEquals(float i) {
        this.setBottom(i);
    }

    // it is fatiguing to write this much code

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.setLeft(topLeft.x);
        this.setTop(topLeft.y);
        refresh();
    }

    public Point getMidTop() {
        return midTop;
    }

    public void setMidTop(Point midTop) {
        setLeft(midTop.x - (w / 2));
        setTop(midTop.y);
        refresh();
    }

    public Point getTopRight() {
        return topRight;
    }

    public void setTopRight(Point topRight) {
        setRight(topRight.x);
        setTop(topRight.y);
        refresh();
    }

    public Point getMidLeft() {
        return midLeft;
    }

    public void setMidLeft(Point midLeft) {
        setLeft(midLeft.x);
        setTop(midLeft.y - (h / 2));
        refresh();
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        setLeft(center.x - (w / 2));
        setTop(center.y - (h / 2));
        refresh();
    }

    public Point getMidRight() {
        return midRight;
    }

    public void setMidRight(Point midRight) {
        setRight(midRight.x);
        setTop(midRight.y - (h / 2));
        refresh();
    }

    public Point getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(Point bottomLeft) {
        setLeft(bottomLeft.x);
        setBottom(bottomLeft.y);
        refresh();
    }

    public Point getMidBottom() {
        return midBottom;
    }

    public void setMidBottom(Point midBottom) {
        setLeft(midBottom.x - (w / 2));
        setBottom(midBottom.y);
        refresh();
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        setRight(bottomRight.x);
        setBottom(bottomRight.y);
        refresh();
    }

    public float getRadius() {
        return getW() / 2.0f;
    }

    public boolean colliderect(Rect other) {
        return this.getRight() >= other.getLeft() &&
                this.getLeft() <= other.getRight() &&
                this.getBottom() >= other.getTop() &&
                this.getTop() <= other.getBottom();
    }

    public boolean overlaps(Rect other) {
        return other.collidepoint(this.getCenter());
    }

    public boolean contains(Rect other) {
        if (this.getRight() >= other.getRight()
            && this.getLeft() <= other.getLeft()
            && this.getBottom() >= other.getBottom()
            && this.getTop() <= other.getTop()) {
            return true;
        }

        return false;
    }

    public boolean collidepoint_x(Point point) {
        return point.x > getLeft() && point.x < getRight();
    }

    public boolean collidepoint_y(Point point) {
        return point.y > getTop() && point.y < getBottom();
    }

    public boolean collidepoint(Point point) {
        return collidepoint_x(point) && collidepoint_y(point);
    }

    public boolean collidepoint_circle(Point touchPoint) {
        return getCenter().to(touchPoint).getMagnitude() <= getRadius();
    }
}
