var data_rep = document.getElementById('info_rep');
var data_ad = document.getElementById('info_adverb');

var rep_count = [];
for (key in data_rep) {
	rep_count.push(data_rep[key]);
}

var ad_count = [];
for (key in data_ad) {
	rad_count.push(data_ad[key]);
}

var ctx = document.getElementById('repetition').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        // 반복어
        labels: Object.keys(data_rep),
        datasets: [{
            label: '반복어 빈도',
            // 빈도
            data: rep_count,
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        responsive: false,
        legend: {
            display: false,
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true,
                    stepSize : 2,
					max: 10
                }
            }]
        },
        title: {
            display: true,
            text: '반복어 빈도'
        }
    }
});

var ctx = document.getElementById('adverb').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        // 부사구
        labels: Object.keys(data_ad),
        datasets: [{
            label: '부사구 빈도',
            // 빈도
            data: ad_count,
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)'
                // 'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)'
                // 'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        responsive: false,
        legend: {
            display: false,
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true,
                    stepSize : 2,
                    max: 10
                }
            }]
        },
        title: {
            display: true,
            text: '부사구 빈도'
        }
    }
});