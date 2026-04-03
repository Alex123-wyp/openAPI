import type {
  ActionType,
  ProColumns,
  ProDescriptionsItemProps,
} from '@ant-design/pro-components';
import {
  PageContainer,
  ProDescriptions,
  ProTable,
} from '@ant-design/pro-components';
import { useIntl, useRequest } from '@umijs/max';
import { Drawer, Popconfirm, Tag, message, Button} from 'antd';
import React, { useRef, useState } from 'react';
import {
  deleteInterfaceInfoUsingPost,
  listInterfaceInfoByPageUsingPost,
  offlineInterfaceInfoUsingPost,
  onlineInterfaceInfoUsingPost,
} from '@/services/openapi-backend/interfaceInfoController';
import CreateForm from '../table-list/components/CreateForm';
import UpdateForm from '../table-list/components/UpdateForm';

type TableRequestParams = API.InterfaceInfoQueryRequest & {
  current?: number;
  pageSize?: number;
};

const statusMap: Record<number, { text: string; color: string }> = {
  0: { text: 'Offline', color: 'default' },
  1: { text: 'Online', color: 'success' },  
};



const InterfaceInfo: React.FC = () => {
  const actionRef = useRef<ActionType | null>(null);
  const [showDetail, setShowDetail] = useState(false);
  const [currentRow, setCurrentRow] = useState<API.InterfaceInfo>();
  const intl = useIntl();
  const [messageApi, contextHolder] = message.useMessage();

  //take returned run and store it in a local variable named runDelete
  const { run: runDelete, loading: deleteLoading } = useRequest(
    deleteInterfaceInfoUsingPost,
    {
      manual: true,
      onSuccess: () => {
        messageApi.success('Deleted successfully');
        actionRef.current?.reload?.();
      },
      onError: () => {
        messageApi.error('Delete failed, please try again!');
      },
    },
  );

  const { run: runOnline, loading: onlineLoading } = useRequest(
    onlineInterfaceInfoUsingPost,
    {
      manual: true,
      onSuccess: () => {
        messageApi.success('Published successfully');
        actionRef.current?.reload?.();
      },
      onError: () => {
        messageApi.error('Publish failed, please try again!');
      },
    },
  );

    const { run: runOffline, loading: offlineLoading } = useRequest(
    offlineInterfaceInfoUsingPost,
    {
      manual: true,
      onSuccess: () => {
        messageApi.success('Published successfully');
        actionRef.current?.reload?.();
      },
      onError: () => {
        messageApi.error('Offline failed, please try again!');
      },
    },
  );

  const columns: ProColumns<API.InterfaceInfo>[] = [  
    {
      title: 'ID',
      dataIndex: 'id',
      width: 80,
      search: false,
    },
    {
      title: 'Name',
      dataIndex: 'name',
      render: (_, entity) => (
        <a
          onClick={() => {
            setCurrentRow(entity);
            setShowDetail(true);
          }}
        >
          {entity.name || '-'}
        </a>
      ),
      formItemProps: {
        rules: [{
          required: true,
          message: "Name required!"
        }]
      },
      
    },
    {
      title: 'URL',
      dataIndex: 'url',
      copyable: true,
      ellipsis: true,
    },
    {
      title: 'Method',
      dataIndex: 'method',
      search: false,
      render: (_, entity) => {
        const method = entity.method || 'UNKNOWN';
        const color =
          method === 'GET'
            ? 'green'
            : method === 'POST'
              ? 'blue'
              : method === 'PUT'
                ? 'orange'
                : method === 'DELETE'
                  ? 'red'
                  : 'default';

        return <Tag color={color}>{method}</Tag>;
      },
    },
    {
      title: 'Status',
      dataIndex: 'status',
      search: false,
      render: (_, entity) => {
        const value = entity.status ?? -1;
        const status = statusMap[value];

        if (!status) {
          return <Tag>{value}</Tag>;
        }

        return <Tag color={status.color}>{status.text}</Tag>;
      },
    },
    {
      title: 'Description',
      dataIndex: 'description',
      valueType: 'textarea',
      ellipsis: true,
    },
    {
      title: 'User ID',
      dataIndex: 'userId',
      search: false,
    },
    {
      title: 'Updated Time',
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      search: false,
      sorter: true,
    },
    {
      title: 'Operation',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <UpdateForm
          trigger={<a>Edit</a>}
          key="edit"
          onOk={actionRef.current?.reload}
          values={record}
          columns={columns}
        />,


      //Publish button
        record.status === 0 ? 
        <Popconfirm
          key="publish"
          title="Publish this interface?"
          okButtonProps={{ loading: onlineLoading }}
          onConfirm={async () => {
            if (record.id === undefined) {
              messageApi.error('Missing interface id');
              return;
            }
            await runOnline({ id: record.id });
          }}
        >

        {/*Popconfirm component use children component <a>Publish</a> as a trigger */}
          <a>Publish</a>
        </Popconfirm> : null,

         //offline button
        record.status === 1 ? 
        <Popconfirm
          key="offline"
          title="Offline this interface?"
          okButtonProps={{ loading: offlineLoading }}
          onConfirm={async () => {
            if (record.id === undefined) {
              messageApi.error('Missing interface id');
              return;
            }
            await runOffline({ id: record.id });
          }}
        >

        {/*Popconfirm component use children component <a>Offline</a> as a trigger */}
          <a>Offline</a>
        </Popconfirm> : null,

        //Delete button
        <Popconfirm
          key="delete"
          title="Delete this interface?"
          color="red"
          okButtonProps={{ loading: deleteLoading }}
          onConfirm={async () => {
            if (record.id === undefined) {
              messageApi.error('Missing interface id');
              return;
            }
            await runDelete({ id: record.id });
          }}
        >

        {/*Popconfirm component use children component <a>Delete</a> as a trigger */}
        
          <Button danger>Delete</Button>
        </Popconfirm>,

        
      ],
    },
  ];

  const detailColumns: ProDescriptionsItemProps<API.InterfaceInfo>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
    },
    {
      title: 'Name',
      dataIndex: 'name',
    },
    {
      title: 'Description',
      dataIndex: 'description',
      valueType: 'textarea',
    },
    {
      title: 'URL',
      dataIndex: 'url',
      copyable: true,
    },
    {
      title: 'Method',
      dataIndex: 'method',
    },
    {
      title: 'Request Header',
      dataIndex: 'requestHeader',
      valueType: 'textarea',
    },
    {
      title: 'Response Header',
      dataIndex: 'responseHeader',
      valueType: 'textarea',
    },
    {
      title: 'Status',
      dataIndex: 'status',
    },
    {
      title: 'User ID',
      dataIndex: 'userId',
    },
    {
      title: 'Create Time',
      dataIndex: 'createTime',
      valueType: 'dateTime',
      hideInForm: true,
    },
    {
      title: 'Update Time',
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      hideInForm: true,

    },
  ];

  return (
    <PageContainer>
      {contextHolder}
      
      <ProTable<API.InterfaceInfo, TableRequestParams>
        headerTitle={intl.formatMessage({
          id: 'menu.list.table-list',
          defaultMessage: 'Interface Info',
        })}
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <CreateForm columns={columns} reload={actionRef.current?.reload} />,
        ]}
        request={async (params, sorter) => {
          const sortField = Object.keys(sorter ?? {})[0];
          const rawSortOrder = sortField
            ? (sorter as Record<string, 'ascend' | 'descend' | undefined>)[sortField]
            : undefined;
          const sortOrder =
            rawSortOrder === 'descend'
              ? 'desc'
              : rawSortOrder === 'ascend'
                ? 'asc'
                : undefined;

          const res = await listInterfaceInfoByPageUsingPost({
            // current: params.current,
            // pageSize: params.pageSize,
            // id: params.id,
            // name: params.name,
            // description: params.description,
            // method: params.method,
            // status: params.status,
            // url: params.url,
            // userId: params.userId,
            // sortField,
            // sortOrder,
            ...params
          });

          return {
            data: res.data?.records ?? [],
            success: true,
            total: res.data?.total ?? 0,
          };
        }}

        columns={columns}
      />
      <Drawer
        width={720}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.id !== undefined && (
          <ProDescriptions<API.InterfaceInfo>
            column={2}
            title={currentRow.name || 'Interface Detail'}
            request={async () => ({
              data: currentRow,
              success: true,
            })}
            params={{
              id: currentRow.id,
            }}
            columns={detailColumns}
          />
        )}
      </Drawer>
      {/* <CreateForm columns={columns} /> */}
    </PageContainer>
  );
};

export default InterfaceInfo;
