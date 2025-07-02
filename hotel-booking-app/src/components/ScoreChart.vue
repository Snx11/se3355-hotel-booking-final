<template>
    <div>
      <h4 class="mt-4">Yorum Puan Dağılımı</h4>
      <canvas ref="chartRef" width="600" height="300"></canvas>
    </div>
  </template>
  
  <script setup lang="ts">
  import { onMounted, ref, watch } from 'vue'
  import {
    Chart,
    BarElement,
    CategoryScale,
    LinearScale,
    Tooltip,
    Title
  } from 'chart.js'
  
  Chart.register(BarElement, CategoryScale, LinearScale, Tooltip, Title)
  
  const props = defineProps<{
    scores: number[]
  }>()
  
  const chartRef = ref<HTMLCanvasElement | null>(null)
  let chartInstance: Chart | null = null
  
  const createChart = () => {
    if (!chartRef.value) return
  
    const scoreCounts = Array(11).fill(0)
    props.scores.forEach(score => {
      const s = Math.round(score)
      if (s >= 0 && s <= 10) scoreCounts[s]++
    })
  
    chartInstance = new Chart(chartRef.value, {
      type: 'bar',
      data: {
        labels: scoreCounts.map((_, i) => i.toString()),
        datasets: [{
          label: 'Kullanıcı Puanları',
          data: scoreCounts,
          backgroundColor: '#0d6efd'
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              stepSize: 1 // Bu artık burada doğru yer
            }
          }
        }
      }
    })
  }
  
  onMounted(() => {
    if (props.scores.length > 0) {
      createChart()
    }
  })
  
  watch(() => props.scores, () => {
    chartInstance?.destroy()
    createChart()
  })
  </script>
  