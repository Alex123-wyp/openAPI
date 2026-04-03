import { PlusOutlined } from '@ant-design/icons';
import {
  type ActionType,
  ProTable,
} from '@ant-design/pro-components';
import { FormattedMessage, useIntl, useRequest } from '@umijs/max';
import { Button, Modal, message } from 'antd';
import React, { useState } from 'react';
import type { FC } from 'react';
import type { ProColumns } from '@ant-design/pro-components';
import { addInterfaceInfoUsingPost } from '@/services/openapi-backend/interfaceInfoController';

interface CreateFormProps {
  reload?: ActionType['reload'];
  columns: ProColumns<API.InterfaceInfo>[];
  // visible: boolean;
}

  /**
   * CreateForm is a function component, it receives props whose type is CreateFormProps
   * */

const CreateForm: FC<CreateFormProps> = (props) => {

  const { reload } = props;
  const { columns } = props;
  const [open, setOpen] = useState(false);
  const [messageApi, contextHolder] = message.useMessage();


  // const { visible } = props;

  /**
   * @en-US International configuration
   * @zh-CN 国际化配置
   * */
  const { run, loading } = useRequest(addInterfaceInfoUsingPost, {
    manual: true,
    onSuccess: () => {
      messageApi.success('Added successfully');
      setOpen(false);
      reload?.();
    },
    onError: () => {
      messageApi.error('Adding failed, please try again!');
    },
  });


  const formColumns = columns.filter((column) => {
    const dataIndex = Array.isArray(column.dataIndex)
      ? column.dataIndex[0]
      : column.dataIndex;

    return !['id', 'userId', 'createTime', 'updateTime', 'option'].includes(
      String(dataIndex ?? ''),
    );
  });

  const headerColumns: ProColumns<API.InterfaceInfoAddRequest>[] = [
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
  ];

  const createColumns = [
    ...formColumns,
    ...headerColumns,
  ] as ProColumns<API.InterfaceInfoAddRequest>[];

  return (
    <>
      {contextHolder}
      <Button
        type="primary"
        icon={<PlusOutlined />}
        onClick={() => {
          setOpen(true);
        }}
      >
        <FormattedMessage id="pages.searchTable.new" defaultMessage="New" />
      </Button>

      <Modal
        open={open}
        onCancel={() => {
          setOpen(false);
        }}
        footer={null}
        destroyOnHidden
      >
        <ProTable<API.InterfaceInfoAddRequest>
          type="form"
          columns={createColumns}
          ghost
          loading={loading}
          onSubmit={async (value) => {
            const payload: API.InterfaceInfoAddRequest = {
              ...value
            };
            await run(payload);
          }}
        />
      </Modal>


    </>
  );
};

export default CreateForm;
