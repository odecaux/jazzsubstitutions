enum class Function {
    Tonic,
    Dominant,
    Subdominant
}

sealed class Degree(val root: Int,private val name : String) {


    abstract val function : Function
    abstract val key : Int
    abstract val mode : Mode
    protected abstract val primitive : List<Int>
    val accord get() = primitive.map { (it + key) % 12 }

    abstract fun copy(mode: Mode) : Degree

    val realRoot : Int get() = (root + key) % 12

    val modalInterchange : Degree
        get() {
            return if( mode == Mode.Maj) this.copy( mode = Mode.Min)
            else this.copy( mode = Mode.Maj)
        }

    val Vof : Degree get() = V(realRoot, mode)
    val subVof : Degree get() = subV(realRoot, mode)
    val subIIof : Degree get() = subV(realRoot, mode)
    val IIof : Degree get() = II(realRoot, mode)

    val display : String
        get() = "$name of $key ${mode.name}"

}
data class I(override val key: Int, override val mode : Mode) : Degree(0, "I") {
    override val function = Function.Tonic
    override fun copy(mode: Mode) = copy(mode = mode, key = key)
    override val primitive : List<Int> get() =
        when(mode) {
            Mode.Maj -> listOf(0,4,7,11)
            Mode.Min -> listOf(0,3,7,10)
        }

}
data class II(override val key: Int, override val mode : Mode) : Degree(2,"II") {
    override val function = Function.Subdominant
    override fun copy(mode: Mode) = copy(mode = mode, key = key)
    override val primitive : List<Int> get() =
        when(mode) {
            Mode.Maj -> listOf(2,5,9,0)
            Mode.Min -> listOf(2,5,8,0)
        }
}
data class III(override val key: Int, override val mode : Mode) : Degree(4,"III") {
    override val function = Function.Dominant
    override fun copy(mode: Mode) = copy(mode = mode, key = key)
    override val primitive : List<Int> get() =
        when(mode) {
            Mode.Maj -> listOf(4,7,11,2)
            Mode.Min -> listOf(3,7,10,2)
        }
}
data class IV(override val key: Int, override val mode : Mode) : Degree(5,"IV") {
    override val function = Function.Subdominant
    override fun copy(mode: Mode) = copy(mode = mode, key = key)
    override val primitive : List<Int> get() =
        when(mode) {
            Mode.Maj -> listOf(5,9,0,4)
            Mode.Min -> listOf(5,8,0,3)
        }
}
data class V(override val key: Int, override val mode : Mode) : Degree(7,"V") {
    override val function = Function.Dominant
    override fun copy(mode: Mode) = copy(mode = mode, key = key)
    override val primitive : List<Int> get() =
        when(mode) {
            Mode.Maj -> listOf(7,11,2,5)
            Mode.Min -> listOf(7,11,2,5)
        }
}
data class VI(override val key: Int, override val mode : Mode) : Degree(9,"VI") {
    override val function = Function.Tonic
    override fun copy(mode: Mode) = copy(mode = mode, key = key)
    override val primitive : List<Int> get() =
        when(mode) {
            Mode.Maj -> listOf(9,0,4,7)
            Mode.Min -> listOf(8,0,3,7)
        }
}
data class VII(override val key: Int, override val mode : Mode) : Degree(11,"VII") {
    override val function = Function.Dominant
    override fun copy(mode: Mode) = copy(mode = mode, key = key)
    override val primitive : List<Int> get() =
        when(mode) {
            Mode.Maj -> listOf(11,2,5,9)
            Mode.Min -> listOf(10,2,5,8)
        }
}
data class subV(override val key: Int, override val mode : Mode) : Degree(1,"subV") {
    override val function = Function.Dominant
    override fun copy(mode: Mode) = copy(mode = mode, key = key)
    override val primitive : List<Int> get() =
        when(mode) {
            Mode.Maj -> listOf(1,5,8,0)
            Mode.Min -> listOf(1,5,8,0)
        }
}

enum class Mode {
    Maj,
    Min
}
