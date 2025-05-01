package commonMain.graphics.transform

sealed interface Transformable {

    interface Scalable : Transformable {

        fun scale(x: Float, y: Float, z: Float): Scalable

    }

    interface Rotatable : Transformable {
        fun rotate(x: Float, y: Float, z: Float): Rotatable
    }

    interface Translatable : Transformable {
        fun translate(x: Float, y: Float, z: Float): Translatable
    }

    interface Movable : Transformable {
        fun move(x: Float, y: Float, z: Float): Movable
    }

}

