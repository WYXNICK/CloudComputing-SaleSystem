<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-select v-model="query.type" placeholder="查找类型" style="flex:2">
                    <el-option key="1" label="顾客ID" value="customerId" @click="query.content= ''"></el-option>
                    <el-option key="2" label="订单时间" value="time"  @click="query.content= ''"></el-option>
                    <el-option key="3" label="订单价格" value="price"  @click="query.content= ''"></el-option>
                    <el-option key="4" label="商品ID" value="productId"  @click="query.content= ''"></el-option>
                    <el-option key="5" label="商品数量" value="number"  @click="query.content= ''"></el-option>
                </el-select>
                <el-input v-if="query.type != 'time'" v-model="query.content" style="flex: 10"></el-input>
                <el-date-picker v-if="query.type == 'time'" v-model="query.content" type="date"  :picker-options="{ timezone: 'GMT' }" placeholder="选择您要查找的日期" :size="size2" style="flex:10" />
                <el-button type="" :icon="Search" @click="performSearch" style="flex: 1">搜索</el-button>
                <el-button type="" :icon="Clock" @click="resetSearch" style="flex: 1;margin-left: 0;">重置</el-button>
                <el-button type="" :icon="Refresh" @click="pullData" style="flex: 1;margin-left: 0;">刷新</el-button>
            </div>
            <div>
                <el-select v-model="query.sequence" style="margin-bottom: 20px; float:right;"   @change="pullData">
                    <el-option key="1" label="默认" value="default"  @change="pullData" ></el-option>
                    <el-option key="2" label="按时间排序" value="time"  @change="pullData" ></el-option>
                    <el-option key="3" label="按订单成交价格排序" value="price"  @change="pullData" ></el-option>
                </el-select>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="orderId" label="订单ID" min-width="100" align="center"></el-table-column>
                <el-table-column prop="customerId" min-width="150" label="顾客ID" align="center"></el-table-column>
                <el-table-column prop="orderTime" min-width="130" label="订单时间" align='center'></el-table-column>
                <el-table-column prop="totalPrice" min-width="130" label="订单价格" align='center'></el-table-column>
                <el-table-column prop="productId" min-width="130" label="商品ID" align="center"></el-table-column>
                <el-table-column prop="quantity" min-width="130" label="购买的商品数量" align="center"></el-table-column>
            </el-table>

            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>

        </div>
    </div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from 'vue';
import { Delete, Edit, Search, Plus, User, Refresh, SuccessFilled, CircleCloseFilled, Document, Clock } from '@element-plus/icons-vue';
import txcRequest from '../service/index.js';

// 表格中的项目
interface TableItem {
    orderId: number,
    customerId: number,
    orderTime: string,
    totalPrice: number,
    productId: number,
    quantity: number,
}
const DialogFlag = ref(false);
const queryStatus = ref("待审核");
const editFlag = ref(false);
const size = 25;
const size2 = ref<'default' | 'large' | 'small'>('default')

const query = reactive({
    pageIndex: 1,
    pageSize: size,
    type: '',
    content: '',
    sequence: 'default',
});


const tableData = ref<Array<TableItem>>([]);
const pageTotal = ref(0);



// const restQueryData = () => {
//     queryData.campus = null;
//     queryData.category1 = null;
//     queryData.category2 = null;
//     queryData.content = null;
//     queryData.date = null;
//     queryData.id = null;
//     queryData.status = 0;
//     queryData.title = null;
// }

// const modifyStatus = (status: number, row: any) => {
//     let pictureArray = row.picture;
//     console.log(pictureArray);
//     if(pictureArray[0] == ""){
//         pictureArray = [];
//     }
//     txcRequest.request({
//         url: 'manage/baike/alter',
//         method: 'PATCH',
//         data: {
//             status: status,
//             post_id: row.post_id,
//             category2: row.category2,
//             createtime: row.createtime,
//             campus: row.campus,
//             category1: row.category1,
//             collectnum: row.collectnum,
//             title:row.title,
//             content:row.content,
//             picture: pictureArray,
//             likenum: row.likenum,
//             name: row.name,
//             readingnum:row.readingnum,
//             id: row.id,
//         }
//     }).then((res: any) => {
//         pullPosts();
//         if (res.code) {

//         }
//     })
// }

// const getNewStatusPosts =(status: number) =>{
//     query.pageIndex = 1;
//     pullPosts();
// }
/**
* searchbar组件定义的事件
* 根据用户提供关键词查找帖子
*/
// const performSearch = () => {
//     query.pageIndex = 1;
//     pullPosts();
// }
/**
 * 从后端获取新的帖子
 */

// const pullPosts = () => {
//     let requestStatus = queryStatus.value === '待审核'? 0 : (queryStatus.value === '已通过'? 1:2); 
//     txcRequest.request({
//         url: 'manage/page/baike',
//         method: 'POST',
//         params: {
//             pageNum: query.pageIndex,
//             pageSize: size
//         },
//         data: {
//             campus: query.type === "校区" ? query.content: null,
//             category1: query.type === "一级目录" ? query.content: null,
//             category2: query.type === "二级目录" ? query.content: null,
//             content: query.type === "帖子内容" ? query.content: null,
//             date: query.type === "日期" ? query.content: null,
//             id: query.type === "用户id" ? query.content: null,
//             title: query.type === "标题" ? query.content: null,
//             status: requestStatus,
//         }
//     }).then((res: any) => {
//         if (!res.code) {
//             tableData.value = res.baike_result;
//             pageTotal.value = res.totalNum;
//             let i =0;
//             for(let item of tableData.value){
//                 if(item.picture[0] === ""){
//                     tableData.value[i].picture = [];                   
//                 }
//                 i++;
//             }
//         }
//     });
// }
// pullPosts();

// const resetSearch = () => {
//     query.pageIndex= 1;
//     query.pageSize= size;
//     query.type= '';
//     query.content= '';
//     pullPosts();
// }

// const handlePageChange = (val: number) => {
//     query.pageIndex = val;
//     pullPosts();
// }

// const showMoreInfo = (row: any) => {
//     editFlag.value = false;
//     DialogFlag.value = true;
//     detailData.value = row;
//     editData.status = row.status === 0? "待审核" : ( row.status === 1? "已通过":"未通过"); 
//     editData.category1 = row.category1;
//     editData.category2 = row.category2;
//     editData.content = row.content;
//     editData.title = row.title;
//     editData.campus = row.campus;
// }

const pullData = () => {
    txcRequest.request({
        url: '/orders/getAll',
        method: 'GET',
        params: {
            pageIndex: query.pageIndex,
            pageSize: query.pageSize,
            type: query.type,
            keyword: query.content,
            sequence: query.sequence
        }
    }
    ).then((res: any) => {
        if (!res.status) {
            tableData.value = res.data;
            pageTotal.value = res.pageNum;
        }
    }).catch((err: any) => {
        console.error(err);
    })
}

const performSearch = () => {
    txcRequest.request({
        url: '/orders/getAll',
        method: 'GET',
        params: {
            pageIndex: 1,
            pageSize: query.pageSize,
            type: query.type,
            keyword: query.content,
            sequence:  query.sequence
        }
    }).then((res: any) => {
        if (!res.type) {
            query.pageIndex = 1;
            tableData.value = res.data;
            pageTotal.value = res.pageNum;
        }
    }).catch((err: any) => {
        console.error(err);
    })
}

const resetSearch = () => {
    query.pageIndex = 1;
    query.type = "";
    query.content = "";
    pullData();
}

pullData();

const handlePageChange = (val: number) => {
    query.pageIndex = val;
    pullData();
}



</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
    display: flex;
    flex-direction: row;
    gap: 20px;
}

.handle-select {
    width: 120px;
}

.table {
    width: 100%;
    font-size: 14px;
}

.red {
    color: #F56C6C;
}

.green {
    color: #5cbf63
}

.blue {
    color: rgb(63, 158, 255)
}

.mr10 {
    margin-right: 10px;
}

.image-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    gap: 10px;
}

.tag-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    gap: 3px;
}

.table-td-thumb {
    width: calc(33.33% - 10px);
    height: auto;
}

.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}

.table-title {
    text-align: center;
}

:deep(.el-form-item__label) {
    font-weight: 600;
}


:deep() .el-popper {
    max-width: 40% !important;
}

.el-button+.el-button {
    margin-left: 0px;
}
</style>

