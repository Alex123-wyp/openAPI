import { PageContainer } from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import React, { useEffect, useState } from 'react';
import { Avatar, Button, List, Skeleton } from 'antd';
import { getInterfaceInfoByIdUsingGet, listInterfaceInfoByPageUsingPost } from '@/services/openapi-backend/interfaceInfoController';

/**
 * 
 * Main page for user
 */

const Main: React.FC = () => {

  type ListItem = API.InterfaceInfo & {
    loading?: boolean
  }

  const PAGE_SIZE = 3;

  const [initLoading, setInitLoading] = useState(true);
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<ListItem[]>([]);
  const [list, setList] = useState<API.InterfaceInfo[]>([]);
  const [total, setTotal] = useState<number>(0);
  const [page, setPage] = useState(1);
  


  const fetchData = async (current: number, pageSize: number) => {
      
      const res = await listInterfaceInfoByPageUsingPost({
        current,
        pageSize,
      });

      return {
        records: res?.data?.records ?? [],
        total: res?.data?.total ?? 0
      }
  }

  useEffect(() => {
    fetchData(page, PAGE_SIZE).then(({records, total}) => {
      const results = Array.isArray(records) ? records : [];
      setInitLoading(false);
      setData(results);
      setList(results);
    });
  }, []);

  const onLoadMore = () => {
    setLoading(true);
    setList(data.concat(Array.from({ length: PAGE_SIZE }).map(() => ({ loading: true }))));
    const nextPage = page + 1;
    setPage(nextPage);
    fetchData(nextPage, PAGE_SIZE).then(({records, total}) => {
      const results = Array.isArray(records) ? records : [];
      const newData = data.concat(results);
      setData(newData);
      setList(newData);
      setLoading(false);
      // Resetting window's offsetTop so as to display react-virtualized demo underfloor.
      // In real scene, you can using public method of react-virtualized:
      // https://stackoverflow.com/questions/46700726/how-to-use-public-method-updateposition-of-react-virtualized
      window.dispatchEvent(new Event('resize'));
    });
  };

    const loadMore =
    !initLoading && !loading ? (
      <div
        style={{
          textAlign: 'center',
          marginTop: 12,
          height: 32,
          lineHeight: '32px',
        }}
      >
        <Button onClick={onLoadMore}>loading more</Button>
      </div>
    ) : null;


  return (
    <PageContainer title="Open API">

      <List
      className="my-list"
      loading={initLoading}
      itemLayout="horizontal"
      loadMore={loadMore}
      dataSource={list}
      renderItem={(item) => (
        <List.Item
          actions={[<a key="list-loadmore-more">more</a>]}
        >
            <List.Item.Meta
              title={<a href="https://ant.design">{item.name}</a>}
              description={item.description}
            />
            <div>content</div>
        </List.Item>
    
  )}/>

        </PageContainer>
)
}
export default Main;

