import { PageContainer } from '@ant-design/pro-components';
import { useMatch, useModel, useParams } from '@umijs/max';
import React, { useEffect, useState } from 'react';
import { Button, List, Card, Descriptions, Form, Input } from 'antd';
import { getInterfaceInfoByIdUsingGet, listInterfaceInfoByPageUsingPost } from '@/services/openapi-backend/interfaceInfoController';


/**
 * 
 * Main page for user
 */


const Main: React.FC = () => {

  const PAGE_SIZE = 3;
  const [initLoading, setInitLoading] = useState(true);
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<API.InterfaceInfo>();
  const params = useParams();
  const fetchData = async (params: API.getInterfaceInfoByIdUsingGETParams) => {
      
      const res = await getInterfaceInfoByIdUsingGet(params);
      return res.data

  }

  useEffect(() => {
    
    fetchData(params).then((res) => {
      const results = Array.isArray(res) ? res : null;
      setInitLoading(false);
      setData(res);
      
    });
  }, []);


  return (
    <PageContainer title="Find API Document" >
        
        <Card>
        {data ? <Descriptions title={data?.name} column={1} bordered={true} extra={<Button>Invoke</Button>}>
        <Descriptions.Item label="Interface name">{data?.name}</Descriptions.Item>
        <Descriptions.Item label="Method">{data?.method}</Descriptions.Item>
        <Descriptions.Item label="Status">{data?.status ? 'Normal' : 'Close'}</Descriptions.Item>
        <Descriptions.Item label="Url">{data?.url}</Descriptions.Item>
        <Descriptions.Item label="Request Params">{data?.requestParams}</Descriptions.Item>
        <Descriptions.Item label="Request Head">{data?.requestHeader}</Descriptions.Item>
        <Descriptions.Item label="Response Head">{data?.responseHeader}</Descriptions.Item>
        <Descriptions.Item label="Create Time">{data?.createTime}</Descriptions.Item>
        

        <Descriptions.Item label="Description">
            {data?.description}
        </Descriptions.Item>

        <Descriptions.Item label="Create Time">
            {data?.createTime}
        </Descriptions.Item>

        <Descriptions.Item label="Update Time">
            {data?.updateTime}
        </Descriptions.Item>

        </Descriptions>
        : (
            <> Interface do not exists!!!</>
        )
        }
        </Card>

        <Card>

            <Form
            name='invoke'
            layout= 'vertical'
            >
               <Form.Item name="requestParams" label="Params" >
                    <Input.TextArea rows={6} />
                </Form.Item>

                <Form.Item  name="submit" >
                    
                    <Button type="primary">
                        
                        Test
                        
                    </Button>                    
                    
                </Form.Item>
                


            </Form>
            
            
        </Card>
  
    </PageContainer>
)
}

export default Main;
