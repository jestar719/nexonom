package cn.joestar.nexonom.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cn.joestar.nexonom.ui.theme.NexonomTheme
import java.util.*
import kotlin.math.max


/**
 * [factory] is a function to provider [IFlexArrangement] ,it's a strategy to location item
 * Default value is [FlexArrangementStart]
 * there provider some other can use [FlexArrangementCenter],[FlexArrangementSpaceBetween],[FlexArrangementSpaceAround],[FlexArrangementSpaceEvenly]
 * If want has a special layout, can implement [IFlexArrangement] or extend [AbsFlexArrangement]
 */
@Composable
fun FlexLayout(
    modifier: Modifier,
    divider: Dp = 4.dp,
    factory: () -> IFlexArrangement = { FlexArrangementStart() },
    content: @Composable () -> Unit
) {
    val policy = MeasurePolicy { measures, constraints ->
        val childConstraints = Constraints(0, constraints.maxWidth, 0, constraints.maxHeight)
        val div = divider.toPx().toInt()
        val maxWidth = constraints.maxWidth
        val computer = FlexLinesComputer(maxWidth, div)
        measures.map {
            val measure = it.measure(childConstraints)
            computer.compute(measure)
        }
        val height = computer.onComputerEnd()
        val list = computer.getList()
        layout(maxWidth, height) {
            factory().layout(div, maxWidth, list) { child: Placeable, x: Int, y: Int ->
                child.place(x, y)
            }
        }
    }
    Layout(content = content, modifier, measurePolicy = policy)
}


/**
 * The class to help sum date of line
 * [compute] is called when child view measure
 * [onComputerEnd] is called when child view measure complete to provider total height
 */
class FlexLinesComputer(private val maxWidth: Int, private val divider: Int) {
    private val list = LinkedList<FlexLine>()
    private var temp = LinkedList<Placeable>()
    private var x: Int = 0
    private var maxHeight = 0
    private var lineWidth = 0
    private var height = 0

    fun getList() = list

    fun compute(placeable: Placeable): Placeable {
        val end = placeable.width + x
        if (end >= maxWidth) {
            list.add(FlexLine(lineWidth, maxHeight, temp))
            temp = LinkedList<Placeable>()
            height += maxHeight + divider
            x = 0
            maxHeight = 0
            lineWidth = 0
        }
        temp.add(placeable)
        x += placeable.width + divider
        lineWidth += placeable.width
        maxHeight = max(maxHeight, placeable.height)
        return placeable
    }

    fun onComputerEnd(): Int {
        list.add(FlexLine(lineWidth, maxHeight, temp))
        height += maxHeight
        return height
    }
}

/**
 * a pojo for save data of each item line
 * [lineWidth]  the total width by sum item's width
 * [lineHeight] the max height of item
 * [list] item's [Placeable] of line for layout
 */
data class FlexLine(val lineWidth: Int, val lineHeight: Int, val list: List<Placeable>)


/***
 * A strategy to help item layout. it computer and provider item's x,y
 */
interface IFlexArrangement {
    /***
     * call for layout each item.
     * [divider] min divider between two item.
     * [maxWidth] width limit of line by parent
     * [list]  the date of every line, see [FlexLine]
     * [action] the function call to layout item. need call when iterate [FlexLine.list]
     */
    fun layout(
        divider: Int,
        maxWidth: Int,
        list: List<FlexLine>,
        action: (child: Placeable, x: Int, y: Int) -> Unit
    )
}

/**
 * a abstract class as [IFlexArrangement] child
 * fixed item layout flow, and fixed layout strategy vertical
 * design a abstract method [computeStart] to override by child for layout horizontal strategy
 * [space] size between two item
 * [x] item layout x
 * [y] item layout y
 */
abstract class AbsFlexArrangement : IFlexArrangement {
    protected var x: Int = 0
    private var y: Int = 0
    protected var space: Int = 0
    override fun layout(
        divider: Int,
        maxWidth: Int,
        list: List<FlexLine>,
        action: (Placeable, Int, Int) -> Unit
    ) {
        list.forEach {
            computeStart(divider, maxWidth, it)
            val lineHeight = it.lineHeight
            it.list.forEach { p ->
                val offsetY = if (p.height < lineHeight) (lineHeight - p.height) / 2 else 0
                action(p, x, y + offsetY)
                x += p.width + space
            }
            y += lineHeight + divider
        }
        y -= divider
    }

    /**
     * called by every line layout start
     * need reset [x] and compute [space]
     */
    protected abstract fun computeStart(divider: Int, maxWidth: Int, line: FlexLine)
}

/***
 * layout item by start,space is divider
 */
class FlexArrangementStart : AbsFlexArrangement() {
    override fun computeStart(divider: Int, maxWidth: Int, line: FlexLine) {
        x = 0
        space = divider
    }
}

/***
 * layout items at center,space is [divider]
 */
class FlexArrangementCenter : AbsFlexArrangement() {
    override fun computeStart(divider: Int, maxWidth: Int, line: FlexLine) {
        val lineWidth = (line.list.size - 1) * divider + line.lineWidth
        x = (maxWidth - lineWidth) / 2
        space = divider
    }
}


/**
 * layout items at center,space count is item count +1
 */
class FlexArrangementSpaceEvenly : AbsFlexArrangement() {
    override fun computeStart(divider: Int, maxWidth: Int, line: FlexLine) {
        val size = line.list.size + 1
        space = (maxWidth - line.lineWidth) / size
        x = space
    }
}

/**
 *  the first item layout start,last item layout end
 *  used same space,space count is item count -1
 */
class FlexArrangementSpaceBetween : AbsFlexArrangement() {
    override fun computeStart(divider: Int, maxWidth: Int, line: FlexLine) {
        val size = line.list.size - 1
        space = (maxWidth - line.lineWidth) / size
        x = 0
    }
}

/***
 * layout items at center,space count is item count+1
 * but start space and end space is half of other space
 */
class FlexArrangementSpaceAround : AbsFlexArrangement() {
    override fun computeStart(divider: Int, maxWidth: Int, line: FlexLine) {
        val size = line.list.size
        space = (maxWidth - line.lineWidth) / size
        x = space / 2
    }
}


@Preview
@Composable
fun FlexLayoutPreview() {
    val list = LinkedList<String>()
    val random = Random()
    for (i in 0..20) {
        list.add("Test${random.nextInt(16)}")
    }
    NexonomTheme {
        FlexLayout(
            modifier = Modifier.fillMaxWidth(),
            factory = { FlexArrangementSpaceEvenly() }
        ) {
            list.forEach {
                Text(text = it)
            }
        }
    }
}
