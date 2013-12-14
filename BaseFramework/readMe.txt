Widget 文件夹下的类概述

slidingMenu    侧滑组件
twoWayGridView    可水平、垂直方向上滚动的Gridview
bannerView    像横幅一样展示图片的组件
FontFitTextView   字体根据textView的宽度，自适应到合适大小的TextView组件
ImageView360Degree  平面360都展示图片的组件
PagingViewGroup     用于分页显示的ViewGroup
NoScrollListView  NoScrollGridView   套在ScrollView中仍可完全显示

Framework作为lib工程引入到主的工程项目中，但是lib工程中assets目录下
的文件不会被打包到最后的apk文件中去。因此用到支付宝功能时要把framework
中asserts目录下的文件拷贝到主工程中去