{
    xAxis: {
        show:false,
        type: 'category',
        data: ${aoxList}
    },
    yAxis: {
        show:false,
        type: 'value'
    },
    color:['red'],
    series: [{
                data: ${aoList},
                type: 'line'
    }]
}