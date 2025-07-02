<template>
    <div class="chart-container">
      <Bar :data="chartData" :options="chartOptions" />
    </div>
  </template>
  
  <script setup lang="ts">
  import { Bar } from 'vue-chartjs'
  import {
    Chart as ChartJS,
    Title,
    Tooltip,
    Legend,
    BarElement,
    CategoryScale,
    LinearScale,
  } from 'chart.js'
  import { computed } from 'vue'
  
  ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)
  
  const props = defineProps<{ scores: number[] }>()
  
  // 0-10 arası her puandan kaç kişi vermiş?
  const scoreCounts = computed(() => {
    const counts = Array(11).fill(0)
    props.scores.forEach(score => {
      const s = Math.round(score)
      if (s >= 0 && s <= 10) counts[s]++
    })
    return counts
  })
  
  const chartData = computed(() => ({
    labels: Array.from({ length: 11 }, (_, i) => i.toString()),
    datasets: [
      {
        label: 'Puan Sayısı',
        backgroundColor: '#0d6efd',
        data: scoreCounts.value,
      },
    ],
  }))
  
  const chartOptions = {
    responsive: true,
    plugins: {
      legend: { display: false },
      tooltip: { enabled: true },
    },
    scales: {
      y: {
        beginAtZero: true,
        ticks: { stepSize: 1 },
      },
    },
  }
  </script>
  
  <style scoped>
  .chart-container {
    max-width: 600px;
    margin: 20px auto;
  }
  </style>
  