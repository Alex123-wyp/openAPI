
import { FormattedMessage, useIntl, useRequest } from '@umijs/max';
import { Modal, message } from 'antd';
import React, { cloneElement, useCallback, useState } from 'react';
// import { updateRule } from '@/services/ant-design-pro/api';
import {  ProTable} from '@ant-design/pro-components';
import type { ProColumns } from '@ant-design/pro-components';
import { updateInterfaceInfoUsingPost } from '@/services/openapi-backend/interfaceInfoController';

export type FormValueType = {
  target?: string;
  template?: string;
  type?: string;
  time?: string;
  frequency?: string;
} & Partial<API.RuleListItem>;

export type UpdateFormProps = {
  trigger?: React.ReactElement<any>;
  onOk?: () => void;
  values: Partial<API.InterfaceInfo>;
  columns: ProColumns<API.InterfaceInfo>[];
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
  const { onOk, values, trigger, columns } = props;

  const intl = useIntl();

  const [open, setOpen] = useState(false);

  const [messageApi, contextHolder] = message.useMessage();

  const { run } = useRequest(updateInterfaceInfoUsingPost, {
    manual: true,
    onSuccess: () => {
      messageApi.success('Configuration is successful');
      onOk?.();
    },
    onError: () => {
      messageApi.error('Configuration failed, please try again!');
    },
  });

  const onCancel = useCallback(() => {
    setOpen(false);
  }, []);


  const onOpen = useCallback(() => {
    setOpen(true);
  }, []);

  const formColumns = columns.filter((column) => {
    const dataIndex = Array.isArray(column.dataIndex)
      ? column.dataIndex[0]
      : column.dataIndex;

    return !['id', 'userId', 'createTime', 'updateTime', 'option'].includes(
      String(dataIndex ?? ''),
    );
  });

  // const onFinish = useCallback(
  //   async (values?: any) => {
  //     await run({ data: values });

  //     onCancel();
  //   },
  //   [onCancel, run],
  // );

  return (
    <>
      {contextHolder}
      {trigger
        ? cloneElement(trigger, {
            onClick: onOpen,
          })
        : null}

      <Modal
        open={open}
        onCancel={onCancel}
        footer={null}
        destroyOnHidden
      >
        <ProTable<API.InterfaceInfoUpdateRequest>
          type="form"
          columns={formColumns as ProColumns<API.InterfaceInfoUpdateRequest>[]}
          form={{
            initialValues: values,
          }}
          onSubmit={async (value) => {
            const payload: API.InterfaceInfoUpdateRequest = {
              id: values.id,
              ...(value as API.InterfaceInfoUpdateRequest),
            };
            await run(payload);
            onCancel();
          }}
        />
      </Modal>
    </>
  );
};

export default UpdateForm;
