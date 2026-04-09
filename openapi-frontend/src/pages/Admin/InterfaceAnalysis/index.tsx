
import {
  PageContainer,

} from '@ant-design/pro-components';
import { message} from 'antd';
import React, { useEffect, useState } from 'react';
import ReactECharts from 'echarts-for-react';
import { listTopInvokerInterfaceInfoUsingGet } from '@/services/openapi-backend/analysisController';

type InterfaceInvokeItem = {
  interfaceInfoId?: number;
  totalNum?: number;
};

type PieChartItem = {
  name: string;
  value: number;
};



const InterfaceAnalysis: React.FC = () => {

  const [messageApi, contextHolder] = message.useMessage();
  const [chartData, setChartData] = useState<PieChartItem[]>([]);

  const fetchData = async () => {
    try {
      const res = await listTopInvokerInterfaceInfoUsingGet();
      const rawList = (res?.data as InterfaceInvokeItem[] | undefined) ?? [];
      const formattedData = rawList.map((item) => ({
        name: `Interface ${item.interfaceInfoId ?? 'Unknown'}`,
        value: item.totalNum ?? 0,
      }));
      setChartData(formattedData);
    } catch (error) {
      messageApi.error('Failed to load interface invoke analysis');
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const option = {
  tooltip: {
    trigger: 'item'
  },
  legend: {
    top: '5%',
    left: 'center'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: chartData
    }
  ]
};


  return (
    <PageContainer>
      {contextHolder}

      <ReactECharts option={option} />

    </PageContainer>
  );
};

export default InterfaceAnalysis;
