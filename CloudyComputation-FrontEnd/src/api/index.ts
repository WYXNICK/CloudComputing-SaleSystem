import request from '../utils/request';
export const fetchTableData = ()=>{
    return request({
        url: './table-data.json',
        method: 'get'
    })
}
export const fetchData = () => {
    return request({
        url: './table.json',
        method: 'get'
    });
};
